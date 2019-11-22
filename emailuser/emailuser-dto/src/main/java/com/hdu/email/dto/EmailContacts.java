package com.hdu.email.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * 功能描述: 联系人实体类
 * @Author: sixl
 * @Date: 2019/11/22 17:33
 */

@Getter
@Setter
public class EmailContacts {
    private String urid;
    //存储账号
    private String contacts;
    //存储姓名
    private String contactsName;
    //组别
    private String groupid;

    //头像路径
    private String headPic;

}
