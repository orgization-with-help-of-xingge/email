package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.dto.EmailUserParam;
import com.hdu.emailuser.api.user.EmailUserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员controller
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class EmailAdminController {

    @Autowired
    private EmailUserApi emailUserApi;

    @RequestMapping("/index")
    public String index(){
        return "user/index";
    }

    @RequestMapping("/getdata")
    @ResponseBody
    public PageView<EmailUserDto> getDate(EmailUserParam param){
        PageView<EmailUserDto> all = emailUserApi.getAll(param);
        return all;
    }

    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/editUser")
    public String edit(String username,Model model){

        BaseReturnResult result = emailUserApi.queryById(username);
        model.addAttribute("user",result.getObject());
        return "user/edit";
    }

    @RequestMapping(value = "/asyeditUser",method = RequestMethod.POST)
    @ResponseBody
    public BaseReturnResult editUser(EmailUserDto emailUserDto){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = emailUserApi.editUser(emailUserDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

}
