package com.hdu.emailservice.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 草稿箱service
 */
public interface DraftService {
    PageView<DraftMailDto> selAll(DraftMailParam param) throws UnsupportedEncodingException;

    BaseReturnResult selById(DraftMailParam param) throws UnsupportedEncodingException;

    BaseReturnResult delById(List<String> uridin) throws Exception;
}
