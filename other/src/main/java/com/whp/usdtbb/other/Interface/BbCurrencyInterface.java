package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface BbCurrencyInterface {
    public JSONObject currencySelectByStyle();

    public JSONObject BbCurrencyDealSelect(String userid);

    public JSONObject BbCurrencyOfName();

    public JSONObject FbMoneySelectForBb(String userid, String currencyid);
}
