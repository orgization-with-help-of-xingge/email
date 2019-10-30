package com.hdu.emailuser.biz.service.impl;

import com.hdu.email.dto.Users;
import com.hdu.email.mybatis.mapper.UsersMapper;
import com.hdu.emailuser.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users queryById(String id) {
        Users users = usersMapper.selById(id);
        if(users==null){
            return new Users();
        }
        return users;
    }
}
