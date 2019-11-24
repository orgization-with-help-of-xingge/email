package com.hdu.emailuser.api.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;

import java.util.List;

public interface ContactsApi {
    PageView<EmailContacts> queryContacts(EmailContactsParam param);

    BaseReturnResult insContacts(EmailContactsParam param);

    BaseReturnResult editContacts(EmailContactsParam param);

    BaseReturnResult delContacts(List<String> uridin);
}
