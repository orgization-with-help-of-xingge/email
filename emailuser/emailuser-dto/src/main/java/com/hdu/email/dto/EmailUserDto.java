package com.hdu.email.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class EmailUserDto implements Serializable {
    private String username;
    private String pwdHash;
    private String passwd;
    private String pwdAlgoithm;
    private String useForwarding;
    private String fowwardDestination;
    private String useAlias;
    private String alias;

}
