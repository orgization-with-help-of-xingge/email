package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.emailuser.api.user.ContactsApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contacts")
@Slf4j
public class ContactsController {
    @Autowired
    private ContactsApi contactsApi;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
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

    @RequestMapping(value = "/inscontacts",method = RequestMethod.POST)
    private BaseReturnResult insert(@RequestHeader("X-Token") String username,EmailContactsParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.xyz");
            result = contactsApi.insContacts(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/editcontacts",method = RequestMethod.POST)
    private BaseReturnResult updContacts(@RequestHeader("X-Token") String username,EmailContactsParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            if (!param.getUsername().equals(username+"@sixl.xyz")){
                return result;
            }
            result = contactsApi.editContacts(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "delcontacts",method = RequestMethod.POST)
    private BaseReturnResult delContacts(@RequestParam("uridin") ArrayList<String> uridin){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = contactsApi.delContacts(uridin);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
