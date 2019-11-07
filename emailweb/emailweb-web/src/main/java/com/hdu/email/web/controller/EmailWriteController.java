package com.hdu.email.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emailwrite")
public class EmailWriteController {
    @RequestMapping("/write")
    public String toPage(){
        return "EmailWrite";
    }

}
