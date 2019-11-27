package com.hdu.emailservice.api;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;

/**
 * 回收站api
 */
public interface RecycleApi {
    PageView<RecycleDto> queryRecycle(RecycleParam param);

    BaseReturnResult delRecycle(RecycleParam param);

    BaseReturnResult revokeRecycle(RecycleParam param);
}
