package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;
import com.hdu.emailuser.api.user.EmailUserApi;
import com.hdu.emailuser.biz.service.EmailUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "emailUserProvider")
@Slf4j
public class EmailUserProvider implements EmailUserApi {
    @Autowired
    private EmailUserService emailUserService;

    @Override
    public BaseReturnResult queryById(String id) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            EmailUserDto emailUserDto = emailUserService.queryById(id);
            if(emailUserDto.getUsername() != null && !emailUserDto.getUsername().equals("")){
                result.setWhenSuccess("操作成功", emailUserDto);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
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

    @Override
    public BaseReturnResult getNameById(String username) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserService.getNameById(username);
        }catch (Exception e){{
            log.error(e.getMessage());
        }}
        return result;
    }

    @Override
    public BaseReturnResult changePasswd(EmailUserDto emailUserDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserService.updPassword(emailUserDto);
        }catch (Exception e){{
            log.error(e.getMessage());
        }}
        return result;
    }

    @Override
    public BaseReturnResult delUsers(List<String> usernames) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserService.delUser(usernames);
        }catch (Exception e){{
            log.error(e.getMessage());
        }}
        return result;
    }

    @Override
    public PageView<EmailUserDto> getAll(EmailUserParam param) {
        PageView<EmailUserDto> pageView = new PageView<>();
        try {
            pageView = emailUserService.getAll(param);
        }catch (Exception e){{
            log.error(e.getMessage());
        }}
        return pageView;
    }

    @Override
    public BaseReturnResult editUser(EmailUserDto emailUserDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserService.updUser(emailUserDto);
        }catch (Exception e){{
            log.error(e.getMessage());
        }}
        return result;
    }

}
