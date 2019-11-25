package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 回收站dto
 */
@Getter
@Setter
public class RecycleDto implements Serializable {
    private String urid;
    private String messageName;
    private Integer rectype;
    private String sender;
    private String recipients;
    private byte[] content;
    private Date lastUpdated;
}
