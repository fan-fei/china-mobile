package shop.bean;

import com.google.common.collect.Lists;

@lombok.Data
public class SetRecordParam extends BaseParam {

    private DataBen data = new DataBen();

    @lombok.Data
    public static class DataBen {
        private java.util.List<RecordListBean> recordList = Lists.newArrayList();

    }

    @lombok.Data
    public static class RecordListBean {

        private String orderId;

        private String itemId;

        private String useId;

        private String virtualCode;

        private String useAmount;

        private String useDatetime;

        private String useContent;

        private String phone;

        private String virtualCodePass;

    }

}
