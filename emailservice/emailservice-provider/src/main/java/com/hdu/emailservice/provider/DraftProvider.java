package com.hdu.emailservice.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.DraftApi;
import com.hdu.emailservice.biz.service.DraftService;
import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 草稿箱
 */
@Service
@Slf4j
public class DraftProvider implements DraftApi {
    @Autowired
    private DraftService draftService;

    @Override
    public PageView<DraftMailDto> queryAll(DraftMailParam param) {
        PageView<DraftMailDto> pageView = new PageView<>();
        try{
             pageView = draftService.selAll(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult queryDetail(DraftMailParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = draftService.selById(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult deleteMail(List<String> uridin) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = draftService.delById(uridin);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
