package shop;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.bean.BaseResult;

@RestController
@RequestMapping(value = "/chinaMobile", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping(value = "/notifyOrder")
    public BaseResult notifyOrder(@RequestParam String req) {
        return shopService.notifyOrder(req);
    }

    @PostMapping(value = "/resendVirtualCode")
    public BaseResult resendVirtualCode(@RequestParam String req) {
        return shopService.resendVirtualCode(req);
    }

    @PostMapping(value = "/setCodeInvalid")
    public BaseResult setCodeInvalid(@RequestParam String req) {
        return shopService.setCodeInvalid(req);
    }

    @PostMapping(value = "/setVirtualCode")
    public BaseResult setVirtualCode(@RequestParam String req) {
        return shopService.setVirtualCode(req);
    }
}
