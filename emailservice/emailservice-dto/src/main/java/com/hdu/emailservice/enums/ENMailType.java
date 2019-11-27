package com.hdu.emailservice.enums;

/**
 * 读取标识
 */
public enum ENMailType {
    SENDER("send"),
    RECIPIENTS("receive");

    private String code;
    ENMailType(String code){
        this.code = code;
    }

    public String getValue(){
        return code;
    }
}
