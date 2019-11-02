package com.hdu.email.common.util.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageView<T> extends BaseReturnResult {
    private List<T> rows;
    private int total;
    private String sumFieldColumn;
    public PageView(){
    }
    public PageView(List<T> rows,int total){
        this.rows=rows;
        this.total=total;
        setWhenSuccess();
    }
    public PageView(String code,String info){
        setCode(code);
        setInfo(info);
    }
}
