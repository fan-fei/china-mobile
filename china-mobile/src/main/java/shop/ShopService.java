package shop;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.bean.BaseResult;
import shop.bean.ErrorCode;
import shop.bean.NotifyOrderReq;

@Service
public class ShopService {

    public BaseResult getMember(String req) throws Exception {

        BaseResult baseResult = new BaseResult();
        baseResult.setErrorCodeDef(ErrorCode.SUCCESS);

        String secretKey = "RMSMs44C4YRA25w27nRjN399VTUVVBY2eGy6E2v";
        String request = new String(Base64.getDecoder().decode(req));
        String inputSign = request.substring(0, 32);
        String inputJson = request.substring(32);

        NotifyOrderReq notifyOrderReq = new ObjectMapper().readValue(inputJson, NotifyOrderReq.class);

        char[] requestJsonChars = inputJson.toCharArray();
        Arrays.sort(requestJsonChars);
        String md5Sign = DigestUtils.md5DigestAsHex(secretKey.concat(String.valueOf(requestJsonChars)).getBytes()).toLowerCase();
        if (!inputSign.equals(md5Sign)) {
            baseResult.setErrorCodeDef(ErrorCode.FAIL_NEED_PARAMS);
            return baseResult;
        }

        return baseResult;
    }
}
