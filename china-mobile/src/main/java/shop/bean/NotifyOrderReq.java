package shop.bean;

@lombok.Data
public class NotifyOrderReq extends BaseParam {
    private Data data;

    @lombok.Data
    protected static class Data {
        private String discount;
        private String finalFee;
        private String itemId;
        private String orderId;
        private String phone;
        private String price;
        private String quantity;
        private String title;

    }

}
