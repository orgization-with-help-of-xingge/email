package com.hdu.emailuser.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;

import java.util.List;

public interface ContactsService {
    PageView<EmailContacts> queryByUserName(EmailContactsParam param);

    BaseReturnResult insContacts(EmailContactsParam param) throws Exception;
}
