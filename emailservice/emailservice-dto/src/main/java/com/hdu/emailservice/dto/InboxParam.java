package com.hdu.emailservice.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: Inboxparam类
 * @Author: sixl
 * @Date: 2019/11/12 14:50
 */
@Getter
@Setter
public class InboxParam implements Serializable {
    private String urid;
    private String messageName;
    private String sender;
    private String recipients;
    private Date lastUpdated;
    private Integer page = 1;
    private Integer rows = 10;
    private String orderby;
    private String sortdir;
    private Integer hasRead;
    private Date readDate;
    private String recipientsName;
    private String username;
    private Boolean isStar;

}
