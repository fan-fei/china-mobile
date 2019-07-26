package shop;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;
import shop.bean.BaseResult;
import shop.bean.ErrorCode;

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
}
