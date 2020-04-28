package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbKlineNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/BbKlineNews")
public class BbKlineNewsSelectController {
    @Autowired
    private BbKlineNewsInterface bbKlineNewsInterface;

    @PostMapping("BbKlineNewsSelect")
    public JSONObject BbKlineNewsSelect(String currency_news_id) {
        JSONObject json = bbKlineNewsInterface.BbLineNewsSelectById(currency_news_id);
        return json;
    }
}
