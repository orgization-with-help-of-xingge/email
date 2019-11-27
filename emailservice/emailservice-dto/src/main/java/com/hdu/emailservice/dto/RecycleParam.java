package com.hdu.emailservice.dto;

import com.hdu.emailservice.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 回收站param
 */
@Getter
@Setter
public class RecycleParam extends BaseQueryParams {
    private String urid;
    private String messageName;
    private List<String> messageNames;
    private String username;
    private String rectype;
    private String sender;
    private String recipients;
    private byte[] content;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String lastUpdated;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String stopDate;
    private Date lastUpdatedStart;
    private Date lastUpdatedEnd;


}
