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

    public void setErrorCodeDef(BaseResult baseResult) {
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }

    public boolean codeEquals(ErrorCodeDef errorCodeDef) {
        return this.code.equals(errorCodeDef.getCode());

    }

}
