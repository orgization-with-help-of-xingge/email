package com.hdu.emailuser.api.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;

public interface EmailUserApi {
    BaseReturnResult queryById(String id);

    BaseReturnResult queryByUserNameAndPasswd(EmailUserDto emailUserDto);

    BaseReturnResult addUser(EmailUserDto emailUserDto);

    /*
    *@Description: 根据用户地址查询用户name
    *@Param:
    *@return:
    */
    BaseReturnResult getNameById(String username);
}
