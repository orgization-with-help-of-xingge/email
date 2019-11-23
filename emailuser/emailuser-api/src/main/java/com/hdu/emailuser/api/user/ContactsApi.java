package com.hdu.emailuser.api.user;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;

public interface ContactsApi {
    PageView<EmailContacts> queryContacts(EmailContactsParam param);
}
