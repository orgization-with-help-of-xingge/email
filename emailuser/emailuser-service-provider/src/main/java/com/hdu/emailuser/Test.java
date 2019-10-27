package com.hdu.emailuser;

import com.hdu.email.dto.Users;
import com.hdu.email.mybatis.mapper.UsersMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UsersMapper bean = ac.getBean(UsersMapper.class);
        List<Users> users = bean.selAll();
        System.out.println(users);
    }





}
