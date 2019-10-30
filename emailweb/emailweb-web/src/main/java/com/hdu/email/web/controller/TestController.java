package com.hdu.email.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.logging.Logger;

@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){

        return "build Success";
    }

    @RequestMapping("/login")
    public String login(){
        return "main";
    }
}
