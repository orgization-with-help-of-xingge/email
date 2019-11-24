package com.hdu.emailservice.enums;

/**
 * 读取标识
 */
public enum ENMailType {
    SENDER(0),
    RECIPIENTS(1);

    private Integer code;
    ENMailType(Integer code){
        this.code = code;
    }

    public Integer getValue(){
        return code;
    }
}
