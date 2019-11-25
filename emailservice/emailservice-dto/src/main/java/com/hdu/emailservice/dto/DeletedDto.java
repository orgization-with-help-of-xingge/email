package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述: 已删除邮件
 * @Author: sixl
 * @Date: 2019/11/25 18:30
 */
@Getter
@Setter
public class DeletedDto implements Serializable {
    private String urid;
    private String username;
    private String messageName;
}
