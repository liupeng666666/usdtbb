package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbCurrencyInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/BbCurrency")
public class BbCurrencyController {
    @Autowired
    private BbCurrencyInterface bbCurrencyInterface;

    //显示自选区数据
    @GetMapping("currencySelectByStyle")
    public JSONObject currencySelectByStyle() {
        JSONObject json = bbCurrencyInterface.currencySelectByStyle();
        return json;
    }

    //币币交易账户
    @PostMapping("BbCurrencyDealSelect")
    public JSONObject BbCurrencyDealSelect(HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbCurrencyInterface.BbCurrencyDealSelect(pid);
        return json;
    }

//    @PostMapping("BbCurrencyDealSelect")
//    public JSONObject BbCurrencyDealSelect( HttpServletRequest request){
//        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
//        JSONObject json=bbCurrencyInterface.BbCurrencyDealSelect(pid);
//        return json;
//    }

    @PostMapping("BbCurrencyOfName")
    public JSONObject BbCurrencyOfName() {
        JSONObject json = bbCurrencyInterface.BbCurrencyOfName();
        return json;
    }

    @PostMapping("BbCurrencyMoney")
    public JSONObject BbCurrencyMoney(String currencyid, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbCurrencyInterface.FbMoneySelectForBb(pid, currencyid);
        return json;
    }
}
