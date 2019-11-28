package com.hdu.emailservice.biz.service.impl;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.DeletedMapper;
import com.hdu.email.mybatis.mapper.RecycleMapper;
import com.hdu.emailservice.biz.service.RecycleService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.Recipients;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RecycleServiceImpl implements RecycleService {
    @Autowired
    private RecycleMapper recycleMapper;

    @Autowired
    private DeletedMapper deletedMapper;


    @Autowired
    private EmailUserApi emailUserApi;
    @Override
    public PageView<RecycleDto> selRecycle(RecycleParam param) throws Exception {
        PageView<RecycleDto> pageView = new PageView<>();
        if (param.getStartDate()!=null && param.getStopDate()!=null){
            param.setLastUpdatedStart(new Date(Long.parseLong(param.getStartDate())));
            param.setLastUpdatedEnd(new Date(Long.parseLong(param.getStopDate())));
        }
        List<RecycleDto> recycleDtos = recycleMapper.selRecycle(param);
        EmailContentUtil emailContentUtil = new EmailContentUtil();
        for (RecycleDto recycleDto : recycleDtos) {
            emailContentUtil.getRecycleContent(recycleDto);
            //设置收件人姓名
            for (Recipients recipients : recycleDto.getRecipitentList()) {
                BaseReturnResult baseReturnResult = emailUserApi.getNameById(recipients.getRecipients());
                if (baseReturnResult.getSuccess()){
                    recipients.setRecipientsName((String)baseReturnResult.getObject());
                }
            }
            //发件人姓名
            BaseReturnResult senderResult = emailUserApi.getNameById(recycleDto.getSender());
            if (senderResult.getSuccess()){
                recycleDto.setSenderName((String) senderResult.getObject());
            }
        }
        pageView.setRows(recycleDtos);
        pageView.setTotal(recycleMapper.countRecycle(param));
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public BaseReturnResult delRecycle(RecycleParam param) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int i = recycleMapper.delRecycle(param);
        if (i<1){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult revokeRecycle(RecycleParam param) throws Exception {
        //插销逻辑 1.从delete表中删除， 2.从recycle表中删除
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int i = recycleMapper.delRecycle(param);
        int j = deletedMapper.delDeleted(param);
        if (i<1 || j<1){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }
}
