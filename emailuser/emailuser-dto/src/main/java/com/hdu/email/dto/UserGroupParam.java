package com.hdu.email.dto;

import com.hdu.email.common.util.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupParam extends BaseQueryParams {
    private String urid;
    private String username;
    private String groupname;
}
