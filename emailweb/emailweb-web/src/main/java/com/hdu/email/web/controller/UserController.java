package com.hdu.email.web.controller;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailuser.api.user.EmailUserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private EmailUserApi emailUserApi;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "build Success";
    }

    @RequestMapping("/login")
    public String login(){
//        Users users = userApi.queryById("111");
        return "login";
    }

    @RequestMapping(value = "/asylogin",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturnResult asyloggin(EmailUserDto emailUserDto){
        BaseReturnResult result=BaseReturnResult.getFailResult();
        try {
            result=emailUserApi.queryByUserNameAndPasswd(emailUserDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }




}
