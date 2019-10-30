package com.hdu.email.web.controller;

import com.hdu.email.dto.Users;
import com.hdu.emailuser.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.logging.Logger;

@Controller
public class TestController {

    @Autowired
    private UserApi userApi;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "build Success";
    }

    @RequestMapping("/login")
    public String login(){
        Users users = userApi.queryById("111");

        return "main";
    }
}
