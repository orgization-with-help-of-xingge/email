package com.hdu.emailuser.biz.service.impl;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.email.mybatis.mapper.mapper.ContactsMapper;
import com.hdu.emailuser.biz.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public PageView<EmailContacts> queryByUserName(EmailContactsParam param) {
        PageView<EmailContacts> pageView = new PageView<>();
        List<EmailContacts> emailContacts = contactsMapper.selByUserName(param);
        pageView.setRows(emailContacts);
        return pageView;
    }
}
