package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.enums.ENMsgCode;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.emailuser.api.user.EmailUserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@Controller
@RequestMapping("/user")
@Slf4j
public class EmailUserController {

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
    public BaseReturnResult asylogin(EmailUserDto emailUserDto, HttpServletRequest request){
        BaseReturnResult result=BaseReturnResult.getFailResult();
        try {
            result=emailUserApi.queryByUserNameAndPasswd(emailUserDto);
            ServletContext servletContext = request.getServletContext();
            if (ENMsgCode.Success.getValue().equals(result.getCode())){
                EmailUserDto user = (EmailUserDto) result.getObject();
                user.setPasswd(emailUserDto.getPasswd());
                servletContext.setAttribute(emailUserDto.getUsername(),user);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String userMain(){
        return "main";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String userRegister(){
        return "register";
    }

    @RequestMapping(value = "/asyregister",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturnResult asyRegister(EmailUserDto emailUserDto){
        BaseReturnResult result=null;
        try {
            result = emailUserApi.addUser(emailUserDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/userInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturnResult userInfo(String token){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result=emailUserApi.queryById(token);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    private BaseReturnResult logout(@RequestHeader("X-Token") String username, HttpServletRequest request){

        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            ServletContext session = request.getServletContext();
            Object attribute = session.getAttribute(username + "@sixl.xyz");
            //退出登录逻辑：从session中删除对应的用户
            session.removeAttribute(username+"@sixl.xyz");
            Object attribute1 = session.getAttribute(username+"@sixl.xyz");
            result.setWhenSuccess();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/changepasswd",method = RequestMethod.POST)
    @ResponseBody
    private BaseReturnResult changePassWord(EmailUserDto emailUserDto){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserApi.changePasswd(emailUserDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deluser",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturnResult delUser(@RequestParam("username")List<String> usernames){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = emailUserApi.delUsers(usernames);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }


}
