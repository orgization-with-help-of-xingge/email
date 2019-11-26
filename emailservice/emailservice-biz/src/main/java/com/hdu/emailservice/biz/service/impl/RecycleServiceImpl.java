package com.hdu.emailservice.biz.service.impl;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.RecycleMapper;
import com.hdu.emailservice.biz.service.RecycleService;
import com.hdu.emailservice.common.util.EmailContentUtil;
import com.hdu.emailservice.dto.Recipients;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecycleServiceImpl implements RecycleService {
    @Autowired
    private RecycleMapper recycleMapper;


    @Autowired
    private EmailUserApi emailUserApi;
    @Override
    public PageView<RecycleDto> selRecycle(RecycleParam param) throws Exception {
        PageView<RecycleDto> pageView = new PageView<>();
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
        }




        return pageView;
    }
}
