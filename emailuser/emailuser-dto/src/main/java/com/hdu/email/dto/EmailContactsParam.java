package com.hdu.email.dto;

import com.hdu.email.common.util.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailContactsParam extends BaseQueryParams {
    private String urid;
    private String username;
    private String contacts;
    private String groupid;
    private String contacts_name;
    private String head_pic;
}
