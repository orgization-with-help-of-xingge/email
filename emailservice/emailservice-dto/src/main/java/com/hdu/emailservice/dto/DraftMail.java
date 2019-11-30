package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: 草稿箱dto
 * @Author: sixl
 * @Date: 2019/11/30 17:24
 */
@Getter
@Setter
public class DraftMail implements Serializable {
    private String urid;
    private String title;
    private byte[] mailContent;
    private byte[] recipients;
    private byte[] copy;
    private byte[] fileList;
    private Date date;
}
