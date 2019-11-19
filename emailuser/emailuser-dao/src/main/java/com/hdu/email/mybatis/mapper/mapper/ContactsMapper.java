package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;

import java.util.List;

public interface ContactsMapper {
    List<EmailUserDto> selByUserName(EmailUserParam emailUserParam);
}
