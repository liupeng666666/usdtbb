package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.utils.HttpClientUtils;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/BbWallet")
public class BbWalletController {
    @Value("${omniBb.url}")
    private String url;
    @Autowired
    private BbCurrencyDao bbCurrencyDao;

    @PostMapping("Address")
    public JSONObject FbWallet(@RequestParam("currencyid") String currencyid, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        Map<String, String> map = new HashMap<>();
        map.put("userid", pid);
        Map<String, Object> bcmap = bbCurrencyDao.BbCurrencyById(currencyid);
        int chain = Integer.parseInt(bcmap.get("chain").toString());
        String value = null;
        if (chain == 1 || chain == 6) {
            value = HttpClientUtils.doPost(url + "/omniBb/eth", "UTF-8", map);
        } else if (chain == 2) {
            value = HttpClientUtils.doPost(url + "/omniBb/etc", "UTF-8", map);
        } else if (chain == 0 || chain == 5) {
            value = HttpClientUtils.doPost(url + "/omniBb/btc", "UTF-8", map);
        } else if (chain == 3) {
            value = HttpClientUtils.doPost(url + "/omniBb/bch", "UTF-8", map);
        } else if (chain == 4) {
            value = HttpClientUtils.doPost(url + "/omniBb/ltc", "UTF-8", map);
        }
        JSONObject jsonObject = JSONObject.parseObject(value);
        return jsonObject;
    }
}

