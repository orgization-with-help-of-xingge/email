package com.hdu.emailservice.enums;

/**
 * 读取标识
 */
public enum ENReadCode {
    UNREAD(0),
    READ(1),
    REPLY(2),
    TRANSMIT(3);

    private Integer code;
    ENReadCode(Integer code){
        this.code = code;
    }

    public Integer getValue(){
        return code;
    }
}
