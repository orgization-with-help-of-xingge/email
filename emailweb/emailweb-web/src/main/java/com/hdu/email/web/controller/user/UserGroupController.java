package com.hdu.email.web.controller.user;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
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


    @RequestMapping("queryall")
    private PageView<UserGroup> getAll(@RequestHeader("X-Token")String username ){

        PageView<UserGroup> pageView = new PageView<>();
        try {
            pageView = userContactsApi.queryAllGroup(username);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

}
