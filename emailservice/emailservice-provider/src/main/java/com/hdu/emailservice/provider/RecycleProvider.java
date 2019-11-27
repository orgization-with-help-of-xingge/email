package com.hdu.emailservice.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.RecycleApi;
import com.hdu.emailservice.biz.service.RecycleService;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecycleProvider implements RecycleApi {

    @Autowired
    private RecycleService recycleService;

    @Override
    public PageView<RecycleDto> queryRecycle(RecycleParam param) {
        PageView<RecycleDto> pageView = new PageView<>();
        try{
            pageView = recycleService.selRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult delRecycle(RecycleParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = recycleService.delRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult revokeRecycle(RecycleParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = recycleService.revokeRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
