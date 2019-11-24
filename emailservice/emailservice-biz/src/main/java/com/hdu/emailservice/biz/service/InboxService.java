package com.hdu.emailservice.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;

import java.util.List;

/**
 * 功能描述: 邮箱逻辑
 * @Author: sixl
 * @Date: 2019/11/12 11:26
 */
public interface InboxService {
    PageView<Inbox> queryUnReadEmail(InboxParam param) throws Exception;

    PageView<Inbox> queryAll(InboxParam param) throws Exception;

    BaseReturnResult queryEmailById(InboxParam param) throws Exception;

    PageView<Inbox> querySend(InboxParam param) throws Exception;

    BaseReturnResult changeStar(InboxParam param) throws Exception;

    BaseReturnResult delInbox(InboxParam param);
}
