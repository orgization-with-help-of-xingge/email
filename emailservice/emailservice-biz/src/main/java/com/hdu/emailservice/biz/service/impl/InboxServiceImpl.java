package com.hdu.emailservice.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.InboxMapper;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import com.hdu.emailservice.dto.Recipients;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class InboxServiceImpl implements InboxService {
    @Autowired
    private InboxMapper inboxMapper;

    @Autowired
    private EmailUserApi emailUserApi;

    //异常直接抛出
    @Override
    public PageView<Inbox> queryUnReadEmail(InboxParam param) throws Exception {
        EmailContentUtil emailContentUtil = new EmailContentUtil();
        PageHelper.startPage(param.getPage(), param.getRows());
        List<Inbox> emails = inboxMapper.selByRecipNonRead(param);
        PageView<Inbox> pageView = new PageView<>();

        Integer total = inboxMapper.countByRecipNonRead(param);
        PageInfo<Inbox> pageInfo = new PageInfo<>(emails);
        List<Inbox> result = pageInfo.getList();
        for (Inbox inbox : result) {
            emailContentUtil.getSubject(inbox);
            inbox.setMessageBody(null);
        }
        pageView.setRows(result);
        pageView.setTotal(total);
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public PageView<Inbox> queryAll(InboxParam param) throws Exception {
        EmailContentUtil emailContentUtil = new EmailContentUtil();
        PageHelper.startPage(param.getPage(),param.getRows());
        List<Inbox> inboxes = inboxMapper.selByRecip(param);
        Integer total = inboxMapper.countByRecip(param);
        PageView<Inbox> pageView = new PageView<>();
        for (Inbox inbox : inboxes) {
            BaseReturnResult nameById = emailUserApi.getNameById(inbox.getSender());
            Boolean success = nameById.getSuccess();
            if (nameById.getSuccess()){
                inbox.setSenderName((String) nameById.getObject());
            }
            emailContentUtil.getSubject(inbox);
            inbox.setMessageBody(null);
        }
        pageView.setTotal(total);
        pageView.setRows(inboxes);
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public BaseReturnResult queryEmailById(InboxParam param) throws Exception {
        EmailContentUtil emailContentUtil = new EmailContentUtil();
        BaseReturnResult result = BaseReturnResult.getFailResult();
        Inbox inbox = inboxMapper.selById(param);
        if (inbox.getHasRead() == 0){
            inbox.setUrid(IdUtil.simpleUUID());
            inbox.setHasRead(1);
            inbox.setReadDate(new Date());
            inboxMapper.insRead(inbox);
        }else {
            inbox.setReadDate(new Date());
            inboxMapper.updRead(inbox);
        }
        emailContentUtil.getContent(inbox);
        //设置收信人
        for (Recipients recipient : inbox.getRecipients()) {
            BaseReturnResult nameById = emailUserApi.getNameById(recipient.getRecipients());
            if (nameById.getSuccess()){
                recipient.setRecipientsName((String) nameById.getObject());
            }
        }
        //设置抄送人
        for (Recipients copy : inbox.getCopys()) {
            BaseReturnResult nameById = emailUserApi.getNameById(copy.getRecipients());
            if (nameById.getSuccess()){
                copy.setRecipientsName((String) nameById.getObject());
            }
        }
        BaseReturnResult nameById = emailUserApi.getNameById(inbox.getSender());
        if (nameById.getSuccess()){
            inbox.setSenderName((String) nameById.getObject());
        }
        inbox.setMessageBody(null);
        result.setObject(inbox);
        result.setWhenSuccess();
        return result;
    }

    @Override
    public PageView<Inbox> querySend(InboxParam param) throws Exception {
        EmailContentUtil emailContentUtil = new EmailContentUtil();
        PageView<Inbox> pageView = new PageView<>();
        PageHelper.startPage(param.getPage(), param.getRows());
        List<Inbox> inboxes = inboxMapper.selBySender(param);
        Integer total = inboxMapper.countBySender(param);
        for (Inbox inbox : inboxes) {
            emailContentUtil.getContent(inbox);
            //设置收信人
            for (Recipients recipient : inbox.getRecipients()) {
                BaseReturnResult nameById = emailUserApi.getNameById(recipient.getRecipients());
                if (nameById.getSuccess()) {
                    recipient.setRecipientsName((String) nameById.getObject());
                }
            }
            //减少传输数据
            inbox.setMessageBody(null);
        }

        pageView.setRows(inboxes);
        pageView.setTotal(total);
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public BaseReturnResult changeStar(InboxParam param) throws Exception {
        BaseReturnResult returnResult = BaseReturnResult.getFailResult();
        Inbox stars=inboxMapper.selStar(param);
        int result = 0;
        if (stars == null){
            param.setUrid(IdUtil.simpleUUID());
            param.setIsStar(true);
            result = inboxMapper.insStar(param);
        }else {
            param.setUrid(stars.getUrid());
            param.setIsStar(!stars.getIsStar());
            result = inboxMapper.updStar(param);
        }
        if (result <=0){
            throw new Exception();
        }
        returnResult.setObject(result);
        returnResult.setWhenSuccess();
        return returnResult;
    }

}
