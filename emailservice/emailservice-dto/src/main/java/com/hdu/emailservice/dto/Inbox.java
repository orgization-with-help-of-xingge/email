package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: 邮箱实体类
 * @Author: sixl
 * @Date: 2019/11/12 10:34
 */
@Getter
@Setter
public class Inbox implements Serializable {
    private String messageName;
    private String repositoryName;
    private String messageState;
    private String errorMessage;
    private String sender;
    private String recipients;
    private String remoteHosts;
    private String remoteAddr;
    private byte[] messageBody;
    private byte[] messageAttributes;
    private Date lastUpdated;
    private boolean hasRead = false;
    private String content;
    private Integer size;
    private String subject;

}
