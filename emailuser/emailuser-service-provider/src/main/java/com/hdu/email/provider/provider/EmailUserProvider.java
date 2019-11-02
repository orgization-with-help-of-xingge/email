package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailuser.api.user.EmailUserApi;
import com.hdu.emailuser.biz.service.EmailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "emailUserProvider")
public class EmailUserProvider implements EmailUserApi {
    @Autowired
    private EmailUserService userService;

    @Override
    public EmailUserDto queryById(String id) {
        return userService.queryById(id);
    }

    @Override
    public BaseReturnResult queryByUserNameAndPasswd(EmailUserDto emailUserDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        EmailUserDto dto = userService.queryByUserNameAndPasswd(emailUserDto);
        if (dto.getUsername()==null){
            return result;
        }
        dto.setPasswd("");
        result.setWhenSuccess("操作成功", dto);
        return result;
    }
}
