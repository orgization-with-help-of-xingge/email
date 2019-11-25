package com.hdu.emailservice.biz.service.impl;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.mybatis.mapper.RecycleMapper;
import com.hdu.emailservice.biz.service.RecycleService;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecycleServiceImpl implements RecycleService {
    @Autowired
    private RecycleMapper recycleMapper;

    @Override
    public PageView<RecycleDto> selRecycle(RecycleParam param) {
        return null;
    }
}
