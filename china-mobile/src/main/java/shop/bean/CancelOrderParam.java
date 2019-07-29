package shop.bean;

@lombok.Data
public class CancelOrderParam extends BaseParam {

    private DataBen data = new DataBen();

    @lombok.Data
    public static class DataBen {

        private String orderId;
        private String memo;
        private String userPhone;

    }

}
