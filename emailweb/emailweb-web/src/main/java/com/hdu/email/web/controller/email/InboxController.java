package com.hdu.email.web.controller.email;

import com.github.pagehelper.Page;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailservice.api.InboxApi;
import com.hdu.emailservice.dto.FileDto;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import com.hdu.emailservice.dto.SendMailDto;
import com.hdu.emailservice.enums.ENReadCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin /*防止跨域请求*/
@RestController
@RequestMapping("/inbox")
@Slf4j
public class InboxController {
    @Autowired
    private InboxApi inboxApi;

    @RequestMapping(value = "/unread",method = RequestMethod.POST)
    private PageView<Inbox> queryUnreadMail(InboxParam param) {
        PageView<Inbox> unReadEmail = new PageView<>();
        try {
            unReadEmail = inboxApi.getUnReadEmail(param);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return unReadEmail;
    }

    @RequestMapping(value = "/receivelist",method = RequestMethod.POST)
    private PageView<Inbox> queryAllEmail(@RequestHeader("X-Token") String username,InboxParam param){
        param.setRecipients(username + "@sixl.xyz");
        PageView<Inbox> pageView = new PageView<>();
        try {
            if (param.getHasRead() == ENReadCode.UNREAD.getValue()){
                pageView=inboxApi.getUnReadEmail(param);
            }else {
                pageView=inboxApi.getAll(param);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return  pageView;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    private BaseReturnResult queryDetail(InboxParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = inboxApi.getEmailById(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/sendlist",method = RequestMethod.POST)
    private PageView<Inbox> querySendEmail(@RequestHeader("X-Token")String username,InboxParam param){
        param.setSender(username + "@sixl.xyz");
        PageView<Inbox> pageView = new PageView<>();
        try {
            String s = new String(param.getRecipientsName().getBytes("iso-8859-1"), "utf-8");
            param.setRecipientsName(s);
            pageView=inboxApi.getSend(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return  pageView;
    }

    @RequestMapping(value = "/changestar",method = RequestMethod.POST)
    private BaseReturnResult changeStar(@RequestHeader("X-Token")String username, @RequestParam("messageNames") ArrayList<String> messageNames){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int sumResult = 0;
        try{
            InboxParam param = new InboxParam();
            for (String messageName : messageNames) {
                param.setMessageName(messageName);
                param.setUsername(username + "@sixl.xyz");
                BaseReturnResult result1=inboxApi.changeStar(param);
                sumResult += (Integer) result1.getObject();
            }
            if (sumResult == messageNames.size()){
                result.setWhenSuccess();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/delInbox",method = RequestMethod.POST)
    private BaseReturnResult delInbox(@RequestHeader("X-Token")String username,InboxParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.xyz");
            result = inboxApi.delInbox(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/sendmail",method = RequestMethod.POST)
    private BaseReturnResult sendMail(@RequestHeader("X-Token")String username, SendMailDto sendMailDto,
                                      @RequestParam(value = "fileList[]",required = false)List<String> fileLists,
                                      HttpServletRequest request){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        List<FileDto> fileDtos = new ArrayList<>();
        try {
            if (fileLists!=null&&fileLists.size()!=0){
                for (String file : fileLists) {
                    String[] split = file.split("&");
                    FileDto fileDto = new FileDto();
                    fileDto.setFilename(split[0]);
                    fileDto.setFtpfilename(split[1]);
                    String filepath = request.getScheme()+"://"+request.getServerName()+":"
                            +request.getServerPort() + request.getContextPath()+"/emailfile/downloadFile/"+split[1];
                    fileDto.setFilepath(filepath);
                    fileDtos.add(fileDto);
                }
                sendMailDto.setFileLists(fileDtos);
            }else {
                sendMailDto.setFileLists(new ArrayList<>());
            }
            EmailUserDto emailUserDto = (EmailUserDto)request.getServletContext().getAttribute(username + "@sixl.xyz");
            result = inboxApi.sendMail(emailUserDto,sendMailDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/savedraft",method = RequestMethod.POST)
    private BaseReturnResult saveDraft(@RequestHeader("X-Token")String username, SendMailDto sendMailDto,
                                       @RequestParam(value = "fileList[]",required = false)List<String> fileLists,HttpServletRequest request){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        List<FileDto> fileDtos = new ArrayList<>();
        try {
            if (fileLists!=null&&fileLists.size()!=0) {
                for (String file : fileLists) {
                    String[] split = file.split("&");
                    FileDto fileDto = new FileDto();
                    fileDto.setFilename(split[0]);
                    fileDto.setFtpfilename(split[1]);
                    String filepath = request.getScheme()+"://"+request.getServerName()+":"
                            +request.getServerPort() + request.getContextPath()+"/emailfile/downloadFile/"+split[1];
                    fileDto.setFilepath(filepath);
                    fileDtos.add(fileDto);
                }
            }
            sendMailDto.setFileLists(fileDtos);
            result = inboxApi.saveDraft(username,sendMailDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getnumber",method = RequestMethod.POST)
    private BaseReturnResult getNumber(@RequestHeader("X-Token")String username){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = inboxApi.getNumber(username+"@sixl.xyz");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getAlert",method = RequestMethod.POST)
    private BaseReturnResult getAlert(@RequestHeader("X-Token")String username){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = inboxApi.getAlert(username+"@sixl.xyz");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

}
