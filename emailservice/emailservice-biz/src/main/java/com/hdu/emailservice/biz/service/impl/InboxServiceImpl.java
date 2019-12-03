package com.hdu.emailservice.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.mybatis.mapper.*;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.*;
import com.hdu.emailservice.enums.ENMailType;
import com.hdu.emailservice.util.MailUtil;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.UnsupportedEncodingException;
import java.util.*;

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

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private DraftMapper draftMapper;

    @Value(value = "${mail.transport.protocol}")
    private String protocol;

    @Value(value = "${mail.smtp.host}")
    private String hosts;


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
            List<FileDto> fileDtos = fileMapper.selByMessageName(inbox.getMessageName());
            if (fileDtos!=null && fileDtos.size()>0){
                inbox.setIsHaveFile(true);
            }
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
        //更新已读
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
        //格式化邮件
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
        //设置发件人
        BaseReturnResult nameById = emailUserApi.getNameById(inbox.getSender());
        if (nameById.getSuccess()){
            inbox.setSenderName((String) nameById.getObject());
        }
        //填写文件
        inbox.setFileLists(fileMapper.selByMessageName(inbox.getMessageName()));
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
            //设置有无文件
            List<FileDto> fileDtos = fileMapper.selByMessageName(inbox.getMessageName());
            if (fileDtos!=null && fileDtos.size()>0){
                inbox.setIsHaveFile(true);
            }
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
            recycleDto.setUsername(param.getUsername());
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

    @Override
    public BaseReturnResult sendMail(EmailUserDto emailUserDto, SendMailDto sendMailDto) throws Exception {
        //发送邮件逻辑：1.得到参数，调用方法发送邮件 2.得到这封邮件的messageName 3.建立邮件和文件之间的关系
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int sum = 0;
        if (sendMailDto.getCopys()==null || sendMailDto.getCopys().size()==0){
            sendMailDto.setCopys(new ArrayList<>());
        }
        MailUtil.sendMail(hosts,protocol,emailUserDto.getUsername()+"@sixl.xyz", sendMailDto.getRecipients(),
                sendMailDto.getCopys(), emailUserDto.getUsername()+"@sixl.xyz",
                emailUserDto.getPasswd(),sendMailDto.getTitle(),sendMailDto.getContent());
        //保证已经全部发完，异步方式完成任务
        new Thread(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> messageNames = inboxMapper.selLastMail(emailUserDto.getUsername()+"@sixl.xyz");
            for (FileDto fileDto : sendMailDto.getFileLists()) {
                for (String messageName : messageNames) {
                    fileDto.setUrid(UUID.randomUUID().toString());
                    fileDto.setMessageName(messageName);
                    fileMapper.insFile(fileDto);
                }
            }
        }).start();

        if (sendMailDto.getUrid()!=null && !"".equals(sendMailDto.getUrid())){
            int i = draftMapper.delDraft(sendMailDto.getUrid());
            if (i<1){
                throw new Exception("操作失败");
            }
        }
//        if (sum<sendMailDto.getFileLists().size()*messageNames.size()){
//            return result;
//        }
        result.setWhenSuccess();

        return result;
    }

    @Override
    public BaseReturnResult insDraft(String username, SendMailDto sendMailDto) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        //草稿箱逻辑：1.转换sendmailDto字段 2.判断是否表格中已存在？插入表格：更新表格
        sendMailDto.setUsername(username+"@sixl.xyz");
        convertBlob(sendMailDto);
        Date date = new Date();
        int i=0;
        if (sendMailDto.getUrid()==null || draftMapper.selById(sendMailDto.getUrid())==null){
            sendMailDto.setDate(date);
            sendMailDto.setLastUpdated(date);
            sendMailDto.setUrid(UUID.randomUUID().toString());
            i = draftMapper.insDraft(sendMailDto);
        }else {
            sendMailDto.setLastUpdated(date);
            i = draftMapper.updDraft(sendMailDto);
        }

        if (i<1){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult selNumber(String s) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        InboxNumber inboxNumber = new InboxNumber();
        //收件箱
        InboxParam param = new InboxParam();
        param.setRecipients(s);
        Integer rec = inboxMapper.countByRecip(param);
        //发件箱
        InboxParam param1 = new InboxParam();
        param1.setSender(s);
        Integer outbox = inboxMapper.countBySender(param1);

        //草稿箱
        DraftMailParam param2 = new DraftMailParam();
        param2.setUsername(s);
        int draft = draftMapper.countDrafts(param2);
        inboxNumber.setDraftcount(draft);
        inboxNumber.setInboxcount(rec);
        inboxNumber.setOutboxcount(outbox);
        result.setObject(inboxNumber);
        result.setWhenSuccess();
        return result;
    }

    //String类型直接转换，list类型先转成json再转换
    public void convertBlob(SendMailDto sendMailDto) throws UnsupportedEncodingException {
        if (sendMailDto.getContent()!=null && !"".equals(sendMailDto.getContent())){
            sendMailDto.setBlobContent(sendMailDto.getContent().getBytes());
        }
        if (sendMailDto.getCopys()!=null&&sendMailDto.getCopys().size()>0){
            sendMailDto.setBlobCopy(JSON.toJSONString(sendMailDto.getCopys()).getBytes("UTF8"));
        }
        if (sendMailDto.getRecipients()!=null&&sendMailDto.getRecipients().size()>0){
            sendMailDto.setBlobRecipients(JSON.toJSONString(sendMailDto.getRecipients()).getBytes("UTF8"));

        }
        if (sendMailDto.getFileLists()!=null&&sendMailDto.getFileLists().size()>0){
            sendMailDto.setBlobFileLists(JSON.toJSONString(sendMailDto.getFileLists()).getBytes("UTF8"));
        }
    }

}
