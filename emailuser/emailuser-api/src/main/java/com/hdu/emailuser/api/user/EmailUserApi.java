package com.hdu.emailuser.api.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;

public interface EmailUserApi {
    BaseReturnResult queryById(String id);

    BaseReturnResult queryByUserNameAndPasswd(EmailUserDto emailUserDto);

    BaseReturnResult addUser(EmailUserDto emailUserDto);
}
