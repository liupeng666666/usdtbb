package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface BbCurrencyNewsInterface {
    public JSONObject getBbCurrencyNewsSelect();

    public JSONObject getBbCurrencyNewsSelectOption(String currency_right, String userid, String currency_news_id, String name, String sort);

    public JSONObject getBbCurrencyNewsSelectByStyle();

    public JSONObject BbCurrencyNewsSelect(String pid, String userid);


    public JSONObject BbCurrencyNewsMoney(Map<String, Object> map);

    public JSONObject BbCurrencyNewsSort(Map<String, Object> map);


    public JSONObject BbCurrencyNewsQB();
}
