package com.hdu.emailservice.dto;

import com.hdu.emailservice.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 回收站dto
 */
@Getter
@Setter
public class RecycleParam extends BaseQueryParams {
    private String urid;
    private String messageName;
    private String username;
    private Integer rectype;
    private String sender;
    private String recipients;
    private byte[] content;
    private Date lastUpdated;

}
