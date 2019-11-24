package com.hdu.email.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述: 用户组织
 * @Author: sixl
 * @Date: 2019/11/20 20:09
 */
@Getter
@Setter
public class UserGroup implements Serializable {
    private String urid;
    private String username;
    private String groupname;
    private List<EmailContacts> contacts;
}
