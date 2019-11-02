package com.hdu.email.mybatis.mapper;

import com.hdu.email.dto.EmailUserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailUserMapper {
    List<EmailUserDto> selAll();

    EmailUserDto selById(@Param(value = "userid") String userid);

    EmailUserDto selByUserNameAndPasswd(EmailUserDto emailUserDto);

}
