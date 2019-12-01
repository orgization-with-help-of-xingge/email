package com.hdu.emailservice.dto;

import com.hdu.emailservice.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DraftMailParam extends BaseQueryParams {
    private String urid;
    private String username;
    private String title;
    private String date;
    private String lastUpdatedStart;
    private String lastUpdatedEnd;
    private String saveDateStart;
    private String saveDateEnd;
    private Date lastUpdatedStartDate;
    private Date lastUpdatedEndDate;
    private Date saveDateStartDate;
    private Date saveDateEndDate;

}
