package com.hdu.emailservice.api;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;

import java.util.List;

public interface InboxApi {
    PageView<Inbox> getUnReadEmail(InboxParam param);

    PageView<Inbox> getAll(InboxParam param);

    BaseReturnResult getEmailById(InboxParam param);

    PageView<Inbox> getSend(InboxParam param);

    BaseReturnResult changeStar(InboxParam param);

    BaseReturnResult delInbox(InboxParam param);
}
