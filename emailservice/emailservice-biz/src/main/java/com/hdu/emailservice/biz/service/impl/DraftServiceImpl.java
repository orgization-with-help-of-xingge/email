package com.hdu.emailservice.biz.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.DraftMapper;
import com.hdu.emailservice.biz.service.DraftService;
import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;
import com.hdu.emailservice.dto.FileDto;
import com.hdu.emailservice.dto.Recipients;
import com.hdu.emailuser.api.user.EmailUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 草稿箱处理逻辑
 */
@Service
public class DraftServiceImpl implements DraftService {
    @Autowired
    private DraftMapper draftMapper;

    @Autowired
    private EmailUserApi emailUserApi;

    @Override
    public PageView<DraftMailDto> selAll(DraftMailParam param) throws UnsupportedEncodingException {
        PageView<DraftMailDto> pageView = new PageView<>();
        if (param.getLastUpdatedStart()!=null && param.getLastUpdatedEnd()!=null){
            param.setLastUpdatedStartDate(new Date(Long.parseLong(param.getLastUpdatedStart())));
            param.setLastUpdatedEndDate(new Date(Long.parseLong(param.getLastUpdatedEnd())));
        }
        if (param.getSaveDateStart()!=null && param.getSaveDateEnd()!=null){
            param.setSaveDateStartDate(new Date(Long.parseLong(param.getSaveDateStart())));
            param.setSaveDateEndDate(new Date(Long.parseLong(param.getSaveDateEnd())));
        }
        PageHelper.startPage(param.getPage(), param.getRows());
        List<DraftMailDto> draftMailDtos = draftMapper.selDrafts(param);
        for (DraftMailDto draftMailDto : draftMailDtos) {
            convert(draftMailDto);
        }
        pageView.setRows(draftMailDtos);
        pageView.setTotal(draftMapper.countDrafts(param));
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public BaseReturnResult selById(DraftMailParam param) throws UnsupportedEncodingException {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        DraftMailDto draftMailDto = draftMapper.selById(param.getUrid());
        convert(draftMailDto);
        result.setObject(draftMailDto);
        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult delById(List<String> uridin) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int sum = 0;
        for (String urid : uridin) {
            sum += draftMapper.delDraft(urid);
        }
        if (sum<uridin.size()){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }

    public void convert(DraftMailDto draftMailDto) throws UnsupportedEncodingException {
        if (draftMailDto.getMailContent()!=null&&draftMailDto.getMailContent().length>0){
            String mailcontent = new String(draftMailDto.getMailContent(),"UTF8");
            draftMailDto.setContent(mailcontent);
        }
        if (draftMailDto.getRecipients()!=null && draftMailDto.getRecipients().length>0){
            //先转成String，在恢复成List
            String rec = new String(draftMailDto.getRecipients(),"UTF8");
            List<Recipients> recipientsList=new ArrayList<>();
            List<String> recs = JSONObject.parseArray(rec, String.class);
            for (String s : recs) {
                BaseReturnResult nameById = emailUserApi.getNameById(s);
                Recipients recipients = new Recipients();
                recipients.setRecipients(s);
                recipients.setRecipientsName(nameById.getObject().toString());
                recipientsList.add(recipients);
            }
            draftMailDto.setRecipientsNameList(recipientsList);
        }

        if (draftMailDto.getCopy()!=null && draftMailDto.getCopy().length>0){
            String copy = new String(draftMailDto.getCopy(),"UTF8");
            List<Recipients> copyLists=new ArrayList<>();
            List<String> copys = JSONObject.parseArray(copy, String.class);
            for (String s : copys) {
                BaseReturnResult nameById = emailUserApi.getNameById(s);
                Recipients recipients = new Recipients();
                recipients.setRecipients(s);
                recipients.setRecipientsName(nameById.getObject().toString());
                copyLists.add(recipients);
            }
            draftMailDto.setCopyNameList(copyLists);
        }

        if (draftMailDto.getFileList()!=null && draftMailDto.getFileList().length>0){
            draftMailDto.setIsHaveFile(true);
            String fileLists = new String(draftMailDto.getFileList(),"UTF8");
            List<FileDto> fileDtos = JSONObject.parseArray(fileLists, FileDto.class);
            draftMailDto.setFileDtoLists(fileDtos);
        }
    }
}
