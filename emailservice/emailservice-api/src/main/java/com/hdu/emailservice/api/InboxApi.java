package com.hdu.emailservice.api;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import com.hdu.emailservice.dto.SendMailDto;

import java.util.List;

public interface InboxApi {
    PageView<Inbox> getUnReadEmail(InboxParam param);

    PageView<Inbox> getAll(InboxParam param);

    BaseReturnResult getEmailById(InboxParam param);

    PageView<Inbox> getSend(InboxParam param);

    BaseReturnResult changeStar(InboxParam param);

    BaseReturnResult delInbox(InboxParam param);

    BaseReturnResult sendMail(EmailUserDto emailUserDto, SendMailDto sendMailDto);

    BaseReturnResult saveDraft(String username, SendMailDto sendMailDto);

    BaseReturnResult getNumber(String s);

    BaseReturnResult getAlert(String s);
}
