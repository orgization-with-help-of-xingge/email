package com.hdu.emailservice.provider;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.RecycleApi;
import com.hdu.emailservice.biz.service.RecycleService;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecycleProvider implements RecycleApi {

    @Autowired
    private RecycleService recycleService;

    @Override
    public PageView<RecycleDto> queryRecycle(RecycleParam param) {
        return null;
    }
}
