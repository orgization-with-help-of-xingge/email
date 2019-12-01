package com.hdu.emailservice.api;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;

import java.util.List;

/**
 * 草稿箱
 */
public interface DraftApi {
    PageView<DraftMailDto> queryAll(DraftMailParam param);

    BaseReturnResult queryDetail(DraftMailParam param);

    BaseReturnResult deleteMail(List<String> uridin);
}
