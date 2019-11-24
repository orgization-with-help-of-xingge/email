package com.hdu.emailuser.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.email.mybatis.mapper.mapper.ContactsMapper;
import com.hdu.emailuser.biz.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;


    @Override
    public PageView<EmailContacts> queryByUserName(EmailContactsParam param) {
        PageView<EmailContacts> pageView = new PageView<>();
        PageHelper.startPage(param.getPage(), param.getRows());
        List<EmailContacts> emailContacts = contactsMapper.selByUserName(param);
        pageView.setRows(emailContacts);
        pageView.setTotal(contactsMapper.countByUserName(param));
        return pageView;
    }

    @Override
    public BaseReturnResult insContacts(EmailContactsParam param) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        param.setUrid(UUID.randomUUID().toString());
        int i = contactsMapper.insContacts(param);
        if (i < 1){
            throw new Exception("插入失败");
        }
        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult updContacts(EmailContactsParam param) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int i = contactsMapper.updContacts(param);
        if (i<1){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }


    @Override
    public BaseReturnResult delContatcs(List<String> uridin) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int  i = contactsMapper.delContatcs(uridin);
        if (i<1){
            throw new Exception("操作失败");
        }
        return result;
    }
}
