package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailservice.api.InboxApi;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        PageView<Inbox> unReadEmail = null;
        try {
            unReadEmail = inboxApi.getUnReadEmail(param);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return unReadEmail;
    }

    @RequestMapping(value = "/fetchlist",method = RequestMethod.POST)
    private PageView<Inbox> queryAllEmail(@RequestHeader("X-Token") String username,InboxParam param, HttpServletRequest request, HttpSession session){
        param.setRecipients(username + "@sixl.xyz");
        PageView<Inbox> pageView = new PageView<>();
        try {
            pageView=inboxApi.getAll(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return  pageView;
    }

}
