package com.hdu.emailuser.biz.service.impl;

import com.hdu.email.common.util.DigestUtil;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.mybatis.mapper.EmailUserMapper;
import com.hdu.emailuser.biz.service.EmailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class EmailUserServiceImpl implements EmailUserService {
    @Autowired
    private EmailUserMapper emailUserMapper;

    @Value("${Email.password.algorithm}")
    private String pwdAlgorithm;

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
            emailUserDto.setPwdAlgoithm(pwdAlgorithm);
            emailUserDto.setPwdHash(DigestUtil.digestString(emailUserDto.getPasswd(),emailUserDto.getPwdAlgoithm()));
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
}
