package com.hdu.emailservice.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.DeletedMapper;
import com.hdu.email.mybatis.mapper.InboxMapper;
import com.hdu.email.mybatis.mapper.RecycleMapper;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.*;
import com.hdu.emailservice.enums.ENMailType;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class InboxServiceImpl implements InboxService {
    @Autowired
    private InboxMapper inboxMapper;

    @Autowired
    private EmailUserApi emailUserApi;

    @Autowired
    private DeletedMapper deletedMapper;

    @Autowired
    private RecycleMapper recycleMapper;

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

    @Override
    public BaseReturnResult delInbox(InboxParam param) {
        //删除逻辑：1.先查询出邮件 2.将邮件插入到回收箱表 3.根据sender和receiver判断类型 4.将邮件id和用户建立联系，插入到t_inbox_delete表中，不在inbox表格中删除列。
        BaseReturnResult result = BaseReturnResult.getFailResult();
        //邮件id
        List<String> messageNames = param.getMessageNames();
        InboxParam inboxParam = new InboxParam();
        DeletedDto deletedDto = new DeletedDto();
        int i=0,j=0;
        for (String messageName : messageNames) {
            inboxParam.setMessageName(messageName);
            Inbox inbox = inboxMapper.selById(inboxParam);
            RecycleDto recycleDto = new RecycleDto();
            recycleDto.setUrid(UUID.randomUUID().toString());
            recycleDto.setMessageName(inbox.getMessageName());
            recycleDto.setRectype(inbox.getRecipient().equals(param.getUsername())? ENMailType.RECIPIENTS.getValue():ENMailType.SENDER.getValue());
            recycleDto.setSender(inbox.getSender());
            recycleDto.setRecipients(inbox.getRecipient());
            recycleDto.setContent(inbox.getMessageBody());
            recycleDto.setLastUpdated(inbox.getLastUpdated());
            j+=recycleMapper.insRecycle(recycleDto);

            //插入delete表
            deletedDto.setUrid(UUID.randomUUID().toString());
            deletedDto.setUsername(param.getUsername());
            deletedDto.setMessageName(messageName);
            i += deletedMapper.insDeleted(deletedDto);
        }
        if (i<messageNames.size() || j<messageNames.size()){
            return result;
        }
        result.setWhenSuccess();
        return result;
    }

}
