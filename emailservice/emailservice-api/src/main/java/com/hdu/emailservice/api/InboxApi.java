package com.hdu.emailservice.api;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;

import java.util.List;

public interface InboxApi {
    PageView<Inbox> getUnReadEmail(InboxParam param);
}
