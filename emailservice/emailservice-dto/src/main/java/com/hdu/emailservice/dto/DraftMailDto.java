package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能描述: 草稿箱dto
 * @Author: sixl
 * @Date: 2019/11/30 17:24
 */
@Getter
@Setter
public class DraftMailDto implements Serializable {
    private String urid;
    private String title;
    private String username;
    private byte[] mailContent;
    private byte[] recipients;
    private byte[] copy;
    private byte[] fileList;
    private String content;
    private List<String> recipientsList;
    private List<String> copyList;
    private List<Recipients> recipientsNameList;
    private List<Recipients> copyNameList;
    private List<FileDto> fileDtoLists;
    private Date savedate;
    private Date lastUpdated;
    private Boolean isHaveFile;

}
