package com.hdu.email.provider.provider;

import com.hdu.email.dto.Users;
import com.hdu.emailuser.api.user.UserApi;
import com.hdu.emailuser.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserProvider implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    public Users queryById(String id) {
        return userService.queryById(id);
    }
}
