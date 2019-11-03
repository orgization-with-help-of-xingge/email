package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailuser.api.user.EmailUserApi;
import com.hdu.emailuser.biz.service.EmailUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "emailUserProvider")
@Slf4j
public class EmailUserProvider implements EmailUserApi {
    @Autowired
    private EmailUserService emailUserService;

    @Override
    public EmailUserDto queryById(String id) {
        return emailUserService.queryById(id);
    }

    @Override
    public BaseReturnResult queryByUserNameAndPasswd(EmailUserDto emailUserDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        EmailUserDto dto = emailUserService.queryByUserNameAndPasswd(emailUserDto);
        if (dto.getUsername()==null){
            return result;
        }
        dto.setPasswd("");
        result.setObject(dto);
        result.setWhenSuccess("操作成功", dto);
        return result;
    }

    @Override
    public BaseReturnResult addUser(EmailUserDto emailUserDto) {
        BaseReturnResult result=null;
        try {
            result=emailUserService.addUser(emailUserDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

}
