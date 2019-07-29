package shop;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import shop.bean.BaseResult;
import shop.bean.CancelOrderParam;
import shop.bean.ErrorCode;
import shop.bean.SetRecordParam;
import shop.bean.SetRecordParam.RecordListBean;
import shop.bean.SetVirtualCodeParam;
import shop.bean.SetVirtualCodeParam.DataBen.VirtualCodesBean;

@Slf4j
@Service
public class ShopService {

    private static final String SECRET_KEY = "HAScKuCxAy3YMARIfzug9e95r0F3MmZ6DY2SSrv9DqcTvUBKH17XDqvBivgvHtf48vgoyoYsJRfVcURgy9822LFVb6bHIYfjBZrxAFWqZGUCEuHR9EwsnfyULdRd0uVs";

    public BaseResult notifyOrder(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        if (!signCheck(req)) {
            baseResult.setErrorCodeDef(ErrorCode.FAIL_SIGN_WRONG);
            return baseResult;
        }

        return baseResult;
    }

    public BaseResult resendVirtualCode(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        if (!signCheck(req)) {
            baseResult.setErrorCodeDef(ErrorCode.FAIL_SIGN_WRONG);
            return baseResult;
        }

        return baseResult;
    }

    public BaseResult setCodeInvalid(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        if (!signCheck(req)) {
            baseResult.setErrorCodeDef(ErrorCode.FAIL_SIGN_WRONG);
            return baseResult;
        }

        return baseResult;
    }

    public BaseResult setVirtualCode(String req) {

        return setVirtualCode("V19072608595411", "http://223.71.96.237:20081/vapi/service/setVirtualCode?req=");
    }

    public BaseResult setRecord(String req) {

        return setRecord("V19072608595411", "http://223.71.96.237:20081/vapi/service/setRecord?req=");
    }

    public BaseResult setRetryVirtualCode(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                BaseResult res = setVirtualCode("V18032816917475", "http://223.71.96.237:20081/vapi/service/retry/setVirtualCode?req=");
                if (res.codeEquals(ErrorCode.SUCCESS)) {
                    continue;
                }
            }
        }).start();

        return baseResult;
    }

    public BaseResult setRetryRecord(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                BaseResult res = setRecord("V18032816917475", "http://223.71.96.237:20081/vapi/service/retry/setRecord?req=");
                if (res.codeEquals(ErrorCode.SUCCESS)) {
                    continue;
                }
            }
        }).start();

        return baseResult;
    }

    public BaseResult cancelOrder(String req) {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        CancelOrderParam s = new CancelOrderParam();

        s.getData().setOrderId("V19072608595411");
        s.getData().setUserPhone("18600432553");
        s.getData().setMemo("撤单原因");

        String chinaMobileReq = null;
        try {
            chinaMobileReq = new ObjectMapper().writer().writeValueAsString(s);
        } catch (JsonProcessingException e) {
            log.error("", e);
        }

        Request request = new Request.Builder()
                .url("http://223.71.96.237:20081/vapi/service/cancelOrder?req=" + getSignedReq(chinaMobileReq))
                .post(RequestBody.create(MediaType.parse("application/json"), "{}")).build();

        try {
            Response response = new OkHttpClient().newCall(request).execute();
            log.info(response.body().string());
        } catch (IOException e) {
            log.error("", e);
        }

        return baseResult;
    }

    private boolean signCheck(String req) {
        String request = new String(Base64.getDecoder().decode(req));
        String inputSign = request.substring(0, 32);
        String inputJson = request.substring(32);
        log.info(SECRET_KEY);
        log.info(inputSign);
        log.info(inputJson);

        char[] requestJsonChars = inputJson.toCharArray();
        Arrays.sort(requestJsonChars);
        String md5Sign = DigestUtils.md5DigestAsHex(SECRET_KEY.concat(String.valueOf(requestJsonChars)).getBytes()).toLowerCase();
        log.info(md5Sign);
        return inputSign.equals(md5Sign);
    }

    private String getSignedReq(String inputJson) {

        char[] requestJsonChars = inputJson.toCharArray();
        Arrays.sort(requestJsonChars);
        String md5Sign = DigestUtils.md5DigestAsHex(SECRET_KEY.concat(String.valueOf(requestJsonChars)).getBytes()).toLowerCase();
        log.info(md5Sign);
        return Base64.getEncoder().encodeToString(md5Sign.concat(inputJson).getBytes());
    }

    private BaseResult setVirtualCode(String orderId, String url) {
        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        SetVirtualCodeParam param = new SetVirtualCodeParam();
        param.getData().setItemId("X17011900041638-01");
        param.getData().setOrderId(orderId);

        VirtualCodesBean v = new VirtualCodesBean();
        v.setVcode("CMV10998601_1234");
        v.setVcodePass("CMV10998601_1234");

        param.getData().getVirtualCodes().add(v);

        try {
            Request request = new Request.Builder().url(url + getSignedReq(new ObjectMapper().writer().writeValueAsString(param)))
                    .post(RequestBody.create(MediaType.parse("application/json"), "{}")).build();

            Response response = new OkHttpClient().newCall(request).execute();

            log.info(response.body().string());

            baseResult.setErrorCodeDef(new ObjectMapper().readValue(response.body().string(), BaseResult.class));
            return baseResult;
        } catch (IOException e) {
            log.error("", e);
            baseResult.setErrorCodeDef(ErrorCode.FAIL);
            return baseResult;
        }
    }

    private BaseResult setRecord(String orderId, String url) {
        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        SetRecordParam param = new SetRecordParam();
        RecordListBean record = new RecordListBean();
        record.setItemId("X17011900041638-01");
        record.setOrderId(orderId);
        record.setUseId("CMV10998601_1234");
        record.setVirtualCode("CMV10998601_1234");
        record.setVirtualCodePass("CMV10998601_1234");
        record.setUseAmount("5.0");
        record.setUseDatetime(LocalDateTime.now().toString());
        record.setUseContent("消费内容");
        record.setPhone("18600432553");

        param.getData().getRecordList().add(record);

        try {
            Request request = new Request.Builder().url(url + getSignedReq(new ObjectMapper().writer().writeValueAsString(param)))
                    .post(RequestBody.create(MediaType.parse("application/json"), "{}")).build();

            Response response = new OkHttpClient().newCall(request).execute();

            log.info(response.body().string());

            baseResult.setErrorCodeDef(new ObjectMapper().readValue(response.body().string(), BaseResult.class));
            return baseResult;
        } catch (IOException e) {
            log.error("", e);
            baseResult.setErrorCodeDef(ErrorCode.FAIL);
            return baseResult;
        }
    }
}
