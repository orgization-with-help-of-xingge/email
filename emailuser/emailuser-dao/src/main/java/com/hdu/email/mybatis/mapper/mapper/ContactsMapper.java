package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;

import java.util.List;

public interface ContactsMapper {
    List<EmailContacts> selByUserName(EmailContactsParam param);
    int insContacts(EmailContactsParam param);
}
