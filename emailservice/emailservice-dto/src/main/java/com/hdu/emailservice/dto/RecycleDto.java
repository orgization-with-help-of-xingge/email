package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 回收站dto
 */
@Getter
@Setter
public class RecycleDto {
    private String urid;
    private Integer type;
    private String sender;
    private String recipients;
    private byte[] content;
    private Date lastUpdated;
}
