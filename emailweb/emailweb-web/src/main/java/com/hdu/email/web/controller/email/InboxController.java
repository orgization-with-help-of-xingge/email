package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.InboxApi;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
