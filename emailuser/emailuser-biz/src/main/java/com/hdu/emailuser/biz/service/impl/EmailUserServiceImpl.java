package com.hdu.emailuser.biz.service.impl;

import com.hdu.email.common.util.DigestUtil;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.mybatis.mapper.mapper.EmailUserMapper;
import com.hdu.emailuser.biz.service.EmailUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class EmailUserServiceImpl implements EmailUserService {
    @Autowired
    private EmailUserMapper emailUserMapper;

    @Value("${Email.password.algorithm}")
    private String pwdAlgorithm;

    @Value("${Email.useForwarding}")
    private String useForwarding;

    @Value("${Email.useAlias}")
    private String useAlias;


    @Override
    public EmailUserDto queryById(String id) {
        EmailUserDto emailUserDto = emailUserMapper.selById(id);
        if(emailUserDto ==null){
            return new EmailUserDto();
        }
        return emailUserDto;
    }

    @Override
    public EmailUserDto queryByUserNameAndPasswd(EmailUserDto emailUserDto) {
        EmailUserDto emailUserDto1=null;
        try {
            emailUserDto.setUsername(emailUserDto.getUsername().split("@")[0]);
            emailUserDto.setPwdAlgorithm(pwdAlgorithm);
            emailUserDto.setPwdHash(DigestUtil.digestString(emailUserDto.getPasswd(),emailUserDto.getPwdAlgorithm()));
            emailUserDto1 = emailUserMapper.selByUserNameAndPasswd(emailUserDto);
            if (emailUserDto1==null){
                return new EmailUserDto();
            }
            return emailUserDto1;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return emailUserDto1;

    }

    @Override
    public BaseReturnResult addUser(EmailUserDto emailUserDto) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            emailUserDto.setPwdAlgorithm(pwdAlgorithm);
            emailUserDto.setPwdHash(DigestUtil.digestString(emailUserDto.getPasswd(),emailUserDto.getPwdAlgorithm()));
            emailUserDto.setUseForwarding(useForwarding);
            emailUserDto.setUseAlias(useAlias);
            int value=emailUserMapper.insertUser(emailUserDto);
            if (value<=0){
                return result;
            }
            result.setWhenSuccess("添加成功");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
