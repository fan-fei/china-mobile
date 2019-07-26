package shop;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chinaMoble", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping(value = "/notifyOrder")
    public String getMember(@RequestParam String req) {
        return shopService.getMember(req);
    }
}
