package com.hdu.email.dto;

import com.hdu.email.common.util.transfer.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmailUserParam extends BaseQueryParams {
    private String urid;
    private String username;
    private String pwdHash;
    private String passwd;
    private String pwdAlgorithm;
    private String useForwarding;
    private String forwardDestination;
    private String useAlias;
    private String alias;
    private String token="admin";
    private String groupid;
    private String groupname;

}
