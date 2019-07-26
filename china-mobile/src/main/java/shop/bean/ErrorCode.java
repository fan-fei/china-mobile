package shop.bean;

import lombok.AllArgsConstructor;

@lombok.Data
public class ErrorCode {

    public static final ErrorCodeDef SUCCESS = new ErrorCodeDef(1001, "验签失败，您所传递的数据签名在API服务器端没有验证通过");
    public static final ErrorCodeDef FAIL_NEED_PARAMS = new ErrorCodeDef(1002, "缺少参数，某个必须传递的参数您没有传递");
    public static final ErrorCodeDef FAIL_FORMAT_INVALID = new ErrorCodeDef(1003, "参数格式错误或不在正确范围内");
    public static final ErrorCodeDef FAIL_IDENTITY_ID_INVALID = new ErrorCodeDef(1004, "无效的Identity ID");
    public static final ErrorCodeDef FAIL_IDENTITY_ID_PROHIBIT = new ErrorCodeDef(1005, "Identity ID被禁用");
    public static final ErrorCodeDef FAIL_WRONG_CODING = new ErrorCodeDef(1006, "错误的编码格式");
    public static final ErrorCodeDef FAIL_RETRY_EXCEED = new ErrorCodeDef(1007, "调用次数超限");
    public static final ErrorCodeDef FAIL_NOT_SUPPORTED_API = new ErrorCodeDef(1008, "不支持此API");
    public static final ErrorCodeDef FAIL_DUPLICATED_ORDER = new ErrorCodeDef(1009, "重复订单");

    @lombok.Data
    @AllArgsConstructor
    public static class ErrorCodeDef {
        private Integer code;
        private String msg;

    }
}
