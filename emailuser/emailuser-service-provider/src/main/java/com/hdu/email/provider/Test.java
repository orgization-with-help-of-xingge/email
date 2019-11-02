package com.hdu.email.provider;

import com.hdu.email.dto.EmailUserDto;
import com.hdu.email.mybatis.mapper.EmailUserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmailUserMapper bean = ac.getBean(EmailUserMapper.class);
        List<EmailUserDto> users = bean.selAll();
        System.out.println(users);
    }





}
