package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.EmailUserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailUserMapper {
    List<EmailUserDto> selAll();

    Integer countAll();

    EmailUserDto selById(@Param(value = "username") String username);

    EmailUserDto selByUserNameAndPasswd(EmailUserDto emailUserDto);

    String getUserName(@Param(value = "username") String username);

    int insertUser(EmailUserDto emailUserDto);

    int updateUser(EmailUserDto emailUserDto);

    int delUser(List<String> usernames);

    int insertUserName(EmailUserDto emailUserDto);
    int updName(EmailUserDto emailUserDto);
}
