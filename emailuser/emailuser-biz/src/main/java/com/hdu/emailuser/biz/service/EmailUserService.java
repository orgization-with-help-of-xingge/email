package com.hdu.emailuser.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;

import java.security.NoSuchAlgorithmException;

public interface EmailUserService {
    EmailUserDto queryById(String id);

    EmailUserDto queryByUserNameAndPasswd(EmailUserDto emailUserDto);

    BaseReturnResult addUser(EmailUserDto emailUserDto);

    BaseReturnResult getNameById(String username);

    BaseReturnResult updPassword(EmailUserDto emailUserDto) throws NoSuchAlgorithmException;
}
