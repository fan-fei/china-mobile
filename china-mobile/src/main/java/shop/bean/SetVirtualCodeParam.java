package shop.bean;

import com.google.common.collect.Lists;

@lombok.Data
public class SetVirtualCodeParam extends BaseParam {

    private DataBen data=new DataBen();

    @lombok.Data
    public static class DataBen {

        private String itemId;

        private String orderId;

        private java.util.List<VirtualCodesBen> virtualCodes= Lists.newArrayList();

        @lombok.Data
        public static class VirtualCodesBen {

            private String vcodePass;

            private String vcode;

        }
    }

}
