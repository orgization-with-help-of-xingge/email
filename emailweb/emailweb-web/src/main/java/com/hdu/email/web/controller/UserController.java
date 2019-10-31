package com.hdu.email.web.controller;

import com.hdu.email.common.transfer.BaseReturnResult;
import com.hdu.email.dto.Users;
import com.hdu.emailuser.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.logging.Logger;

import static javax.swing.text.html.HTML.Tag.P;

@Controller
public class UserController {

    @Autowired
    private UserApi userApi;

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

    @RequestMapping("/asylogin")
    @ResponseBody
    public BaseReturnResult asyloggin(Users users){
        Users users1 = userApi.queryById(users.getUsername());
        BaseReturnResult failResult = BaseReturnResult.getFailResult();
        if (users1!=null){
            failResult.setWhenSuccess("操作成功", users);
        }
        return failResult;

    }
}
