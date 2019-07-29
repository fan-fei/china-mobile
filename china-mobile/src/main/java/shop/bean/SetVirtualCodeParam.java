package shop.bean;

import com.google.common.collect.Lists;

@lombok.Data
public class SetVirtualCodeParam extends BaseParam {

    private DataBen data=new DataBen();

    @lombok.Data
    public static class DataBen {

        private String itemId;

        private String orderId;

        private java.util.List<VirtualCodesBean> virtualCodes= Lists.newArrayList();

        @lombok.Data
        public static class VirtualCodesBean {

            private String vcodePass;

            private String vcode;

        }
    }

}
