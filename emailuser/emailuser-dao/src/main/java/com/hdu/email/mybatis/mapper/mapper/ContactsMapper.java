package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactsMapper {
    List<EmailContacts> selByUserName(EmailContactsParam param);
    int countByUserName(EmailContactsParam param);
    int insContacts(EmailContactsParam param);
    int updContacts(EmailContactsParam param);
    int delContatcs(List<String> uridin);
    int updGroupIdNull(@Param(value = "groupid") String groupid);

}
