package com.hdu.emailservice.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;

public interface RecycleService {
    PageView<RecycleDto> selRecycle(RecycleParam param) throws Exception;

    BaseReturnResult delRecycle(RecycleParam param) throws Exception;

    BaseReturnResult revokeRecycle(RecycleParam param) throws Exception;
}
