package com.hdu.email.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述: 联系人实体类
 * @Author: sixl
 * @Date: 2019/11/22 17:33
 */

@Getter
@Setter
public class EmailContacts implements Serializable {
    private String urid;
    //当前用户
    private String username;
    //存储账号
    private String contacts;
    //存储姓名
    private String contactsName;
    //组别
    private String groupid;

    //头像路径
    private String headPic;

}
