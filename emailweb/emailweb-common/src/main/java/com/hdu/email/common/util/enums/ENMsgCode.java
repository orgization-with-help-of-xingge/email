package com.hdu.email.common.util.enums;

public enum  ENMsgCode implements BasicEnum {

    Success("1"),
    Fail("-1"),
    Exception("-2"),
    Middle("0");

    private String msgcode;

    ENMsgCode(String msgcode){
        this.msgcode=msgcode;
    }

    @Override
    public String getValue() {
        return null;
    }
}
