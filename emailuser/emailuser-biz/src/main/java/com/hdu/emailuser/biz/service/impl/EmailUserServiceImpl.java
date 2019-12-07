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
import org.springframework.transaction.support.TransactionTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
            emailUserDto.setUsername(emailUserDto.getUsername().split("@")[0]);
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

    @Override
    public BaseReturnResult getNameById(String username) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result.setObject(emailUserMapper.getUserName(username));
            result.setWhenSuccess();
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult updPassword(EmailUserDto emailUserDto) throws NoSuchAlgorithmException {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        emailUserDto.setUsername(emailUserDto.getUsername().split("@")[0]);
        //查询原密码对不对
        EmailUserDto param = new EmailUserDto();
        param.setUsername(emailUserDto.getUsername());
        param.setPwdHash(DigestUtil.digestString(emailUserDto.getOrginPassword(),pwdAlgorithm));
        EmailUserDto emailUserDto1 = emailUserMapper.selByUserNameAndPasswd(param);
        //更改密码逻辑
        if (emailUserDto1!=null){
            emailUserDto.setPwdHash(DigestUtil.digestString(emailUserDto.getPasswd(),pwdAlgorithm));
            int value=emailUserMapper.updateUser(emailUserDto);
            if (value<=0){
                result.setWhenFail("更改失败");
                return result;
            }
            result.setWhenSuccess("更改成功");
        }else {
            result.setWhenFail("原密码输入错误");
        }

        return result;
    }

    @Override
    public BaseReturnResult delUser(List<String> usernames) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int sum = emailUserMapper.delUser(usernames);
        if (sum<1){
            return result;
        }
        result.setWhenSuccess();
        return result;
    }
}
