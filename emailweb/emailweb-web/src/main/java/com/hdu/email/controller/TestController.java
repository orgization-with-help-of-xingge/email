package com.hdu.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "build Success";
    }
}
