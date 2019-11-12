package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.EmailUserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailUserMapper {
    List<EmailUserDto> selAll();

    EmailUserDto selById(@Param(value = "username") String username);

    EmailUserDto selByUserNameAndPasswd(EmailUserDto emailUserDto);

    int insertUser(EmailUserDto emailUserDto);
}
