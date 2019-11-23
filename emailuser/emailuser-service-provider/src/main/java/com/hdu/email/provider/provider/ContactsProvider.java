package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.emailuser.api.user.ContactsApi;
import com.hdu.emailuser.biz.service.ContactsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactsProvider implements ContactsApi {
    @Autowired
    private ContactsService contactsService;

    @Override
    public PageView<EmailContacts> queryContacts(EmailContactsParam param) {
        PageView<EmailContacts> pageView = new PageView<>();
        try{
            pageView = contactsService.queryByUserName(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult insContacts(EmailContactsParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = contactsService.insContacts(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
