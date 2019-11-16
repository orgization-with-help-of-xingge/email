package com.hdu.emailservice.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdu.email.common.util.enums.ENMsgCode;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.InboxMapper;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            BaseReturnResult nameById = emailUserApi.getNameById(inbox.getSender());
            if (ENMsgCode.Success.getValue().equals(nameById.getCode())){
                inbox.setSenderName((String) nameById.getObject());
            }
            emailContentUtil.getContent(inbox);
            inbox.setMessageBody(null);
        }
        pageView.setRows(result);
        pageView.setTotal(total);
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
            emailContentUtil.getContent(inbox);
            inbox.setMessageBody(null);
        }
        pageView.setTotal(total);
        pageView.setRows(inboxes);
        pageView.setWhenSuccess();
        return pageView;
    }
}
