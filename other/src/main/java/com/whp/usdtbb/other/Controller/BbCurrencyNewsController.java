package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbCurrencyNewsInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/1 11:16
 * @descrpition :
 */
@RestController
@RequestMapping("/BbCurrencyNews")
public class BbCurrencyNewsController {

    @Autowired
    private BbCurrencyNewsInterface bbCurrencyNewsInterface;

    @PostMapping("BbCurrencyNewsMoney")
    public JSONObject BbCurrencyNewsMoney(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        JSONObject json = bbCurrencyNewsInterface.BbCurrencyNewsMoney(map);
        return json;
    }

    @PostMapping("getBbCurrencyNewsSelect")
    public JSONObject getBbCurrencyNewsSelect() {
        JSONObject json = bbCurrencyNewsInterface.getBbCurrencyNewsSelect();
        return json;
    }

    @PostMapping("getBbCurrencyNewsSelectOptional")
    public JSONObject getBbCurrencyNewsSelectOptional(String currency_right, String currency_news_id, String name, String sort, HttpServletRequest request) {
        String userid = null;
        if (request.getHeader("Authorization") != null) {
            userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        }

        JSONObject json = bbCurrencyNewsInterface.getBbCurrencyNewsSelectOption(currency_right, userid, currency_news_id, name, sort);
        return json;
    }

    @PostMapping("BbCurrencyNewsQB")
    public JSONObject BbCurrencyNewsQB() {
        JSONObject json = bbCurrencyNewsInterface.BbCurrencyNewsQB();
        return json;
    }

    @PostMapping("getBbCurrencyNewsSelectByStyle")
    public JSONObject getBbCurrencyNewsSelectByStyle() {
        JSONObject json = bbCurrencyNewsInterface.getBbCurrencyNewsSelectByStyle();
        return json;
    }
}
