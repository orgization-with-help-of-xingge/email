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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private BaseReturnResult sendMail(@RequestHeader("X-Token")String username, SendMailDto sendMailDto/*,
                                      @RequestParam(value = "fileList[]",required = false)List<String> fileLists*/){
        return null;

    }

}
