package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;
import com.hdu.emailuser.api.user.UserContactsApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述: 当前账号用户组操作
 * @Author: sixl
 * @Date: 2019/11/20 20:08
 */
@CrossOrigin
@RestController
@RequestMapping("usergroup")
@Slf4j
public class UserGroupController {

    @Autowired
    private UserContactsApi userContactsApi;

    @RequestMapping(value = "/queryall",method = RequestMethod.POST)
    private PageView<UserGroup> getAll(@RequestHeader("X-Token") String username,UserGroupParam userGroupParam ){
        PageView<UserGroup> pageView = new PageView<>();
        try {
            userGroupParam.setUsername(username+"@sixl.xyz");
            pageView = userContactsApi.queryAllGroup(userGroupParam);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @RequestMapping(value = "/editgroup",method = RequestMethod.POST)
    private BaseReturnResult editGroup(@RequestHeader("X-Token") String username,UserGroupParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.xyz");
            result= userContactsApi.updGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insgroup",method = RequestMethod.POST)
    private BaseReturnResult insGroup(@RequestHeader("X-Token") String username,UserGroupParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.xyz");
            result = userContactsApi.insGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/delgroup",method = RequestMethod.POST)
    private BaseReturnResult delGroup(@RequestHeader("X-Token") String username,UserGroupParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = userContactsApi.delGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

}
