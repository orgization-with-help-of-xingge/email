package com.hdu.email.common.util.transfer;

import com.hdu.email.common.util.enums.ENMsgCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseReturnResult {
    private String code;
    private String info;
    private String value;
    private Boolean success=false;
    private Object object;

    public BaseReturnResult(){
    }

    public BaseReturnResult(String code){
        this.code=code;
        this.success= ENMsgCode.Success.getValue().equals(code);
    }

    public BaseReturnResult(String code,String info){
        this.code=code;
        this.success= ENMsgCode.Success.getValue().equals(code);
        this.info=info;
    }

    public static BaseReturnResult getFailResult(){
        BaseReturnResult result=new BaseReturnResult();
        result.setSuccess(false);
        result.setCode(ENMsgCode.Fail.getValue());
        result.setInfo("操作失败");
        return result;
    }
    public void setWhenFail(){
        this.code=ENMsgCode.Fail.getValue();
        this.success=false;
    }
    public void setWhenFail(String info){
        setWhenFail();
        this.info=info;
    }
    public void setWhenFail(String info,Object object){
        setWhenFail();
        this.info=info;
        this.object=object;
    }
    public void setWhenSuccess(String info){
        this.info=info;
        this.success=true;
        this.code=ENMsgCode.Success.getValue();
    }
    public void setWhenSuccess(String info,Object object){
        setWhenSuccess(info);
        this.object=object;
    }
    public void setWhenSuccess(){
        setWhenSuccess("操作成功");
    }
    public void setWhenException(){
        this.code=ENMsgCode.Exception.getValue();
        this.success=false;
    }


}
