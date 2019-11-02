package com.hdu.emailuser.biz.service;

import com.hdu.email.dto.EmailUserDto;

public interface EmailUserService {
    EmailUserDto queryById(String id);

    EmailUserDto queryByUserNameAndPasswd(EmailUserDto emailUserDto);
}
