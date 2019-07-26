package shop.bean;

import shop.bean.ErrorCode.ErrorCodeDef;

@lombok.Data
public class BaseResult {
    private Integer code;
    private String msg;

    public void setErrorCodeDef(ErrorCodeDef errorCodeDef) {
        this.code = errorCodeDef.getCode();
        this.msg = errorCodeDef.getMsg();
    }

}
