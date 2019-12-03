package com.hdu.emailservice.provider;

import cn.hutool.db.Page;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailservice.api.InboxApi;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import com.hdu.emailservice.dto.SendMailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InboxProvider implements InboxApi {
    @Autowired
    private InboxService inboxService;

    @Override
    public PageView<Inbox> getUnReadEmail(InboxParam param) {
        PageView<Inbox> pageView = null;
        try {
            pageView = inboxService.queryUnReadEmail(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @Override
    public PageView<Inbox> getAll(InboxParam param) {
        PageView<Inbox> pageView = new PageView<>();
        try {
            pageView=inboxService.queryAll(param);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult getEmailById(InboxParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result=inboxService.queryEmailById(param);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public PageView<Inbox> getSend(InboxParam param) {

        PageView<Inbox> pageView = new PageView<>();
        try {
            pageView=inboxService.querySend(param);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult changeStar(InboxParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result=inboxService.changeStar(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult delInbox(InboxParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = inboxService.delInbox(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult sendMail(EmailUserDto emailUserDto, SendMailDto sendMailDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = inboxService.sendMail(emailUserDto,sendMailDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult saveDraft(String username, SendMailDto sendMailDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = inboxService.insDraft(username,sendMailDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult getNumber(String s) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = inboxService.selNumber(s);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
