package com.hdu.emailuser.biz.service;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface EmailUserService {
    EmailUserDto queryById(String id);

    EmailUserDto queryByUserNameAndPasswd(EmailUserDto emailUserDto);

    BaseReturnResult addUser(EmailUserDto emailUserDto);

    BaseReturnResult getNameById(String username);

    BaseReturnResult updPassword(EmailUserDto emailUserDto) throws NoSuchAlgorithmException;

    BaseReturnResult delUser(List<String> usernames);

    PageView<EmailUserDto> getAll(EmailUserParam param);

    BaseReturnResult updUser(EmailUserDto emailUserDto) throws NoSuchAlgorithmException;
}
