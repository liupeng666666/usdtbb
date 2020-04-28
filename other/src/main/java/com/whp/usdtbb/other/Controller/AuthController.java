package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.*;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 17:50
 * @descrpition :
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private BbAdInterface bbAdInterface;
    @Autowired
    private BbCurrencyNewsInterface bbCurrencyNewsIntreface;
    @Autowired
    private BbMinuteInterface bbMinuteInterface;
    @Autowired
    private BbSellInterface bbSellInterface;
    @Autowired
    private BbOrderInterface bbOrderInterface;
    @Autowired
    private BbKlineInterface bbKlineInterface;
    @Autowired
    private BbEarlyInterface bbEarlyInterface;

    /**
     * 币币首页新闻轮播图
     *
     * @return
     */
    @GetMapping("getAdSelect")
    public JSONObject getAdSelect() {
        JSONObject json = bbAdInterface.getAdSelect();
        return json;
    }

    @PostMapping("getBbCurrencyNews")
    public JSONObject getBbCurrencyNews(String pid, HttpServletRequest request) {
        String userid = null;
        if (request.getHeader("Authorization") != null) {
            userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        }
        JSONObject json = bbCurrencyNewsIntreface.BbCurrencyNewsSelect(pid, userid);
        return json;
    }

    @PostMapping("getBbMinuteSelect")
    public JSONObject getBbMinuteSelect() {
        JSONObject json = bbMinuteInterface.BbMinuteSelect();
        return json;
    }

    @PostMapping("getSellRedis")
    public JSONObject getSellRedis(@RequestParam Map<String, Object> map) {

        JSONObject json = bbSellInterface.BBSellSelect(map);
        return json;
    }

    @PostMapping("getOrderSelect")
    public JSONObject getOrderSelect(@RequestParam Map<String, Object> map) {
        JSONObject json = bbOrderInterface.BbOrderSelect(map);
        return json;
    }

    @PostMapping("getKlineSelect")
    public JSONObject getKlineSelect(@RequestParam Map<String, Object> map) {

        JSONObject json = bbKlineInterface.BbKlineRedis(map);

        return json;
    }

    @PostMapping("BbCurrencyNewsSort")
    public JSONObject BbCurrencyNewsSort(@RequestParam Map<String, Object> map) {

        JSONObject json = bbCurrencyNewsIntreface.BbCurrencyNewsSort(map);

        return json;
    }

    @PostMapping("BbEarlySelect")
    public JSONObject BbEarlySelect() {
        JSONObject json = bbEarlyInterface.BbEarlySelect();
        return json;
    }
}
