package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.emailuser.api.user.ContactsApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/contacts")
@Slf4j
public class ContactsController {
    @Autowired
    private ContactsApi contactsApi;

    @RequestMapping("/query")
    private PageView<EmailContacts> query(@RequestHeader("X-Token") String username,EmailContactsParam param){
        PageView<EmailContacts> pageView =new PageView<>();
        try{
            param.setUsername(username+"@sixl.xyz");
            pageView = contactsApi.queryContacts(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }
}
