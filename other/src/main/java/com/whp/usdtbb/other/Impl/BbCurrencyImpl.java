package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.other.Dao.BbCurrencyNewsDao;
import com.whp.usdtbb.other.Dao.BbMoneyDao;
import com.whp.usdtbb.other.Interface.BbCurrencyInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BbCurrencyImpl implements BbCurrencyInterface {
    @Autowired
    private BbCurrencyDao bbCurrencyDao;
    @Autowired
    private BbCurrencyNewsDao bbCurrencyNewsDao;
    @Autowired
    private BbMoneyDao bbMoneyDao;

    @Override
    public JSONObject currencySelectByStyle() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyDao.currencySelectByStyle();
            json.put("currencyList", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }


    @Override
    public JSONObject BbCurrencyDealSelect(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyDao.BbCurrencyDealSelect(userid);
            List<Map<String, Object>> stdList = bbCurrencyNewsDao.getBbCurrencyNewsStd();
            String cny = RedisUtils.get("CNY", 4);

            json.put("bbDealList", list);
            json.put("cny", cny);
            json.put("stdList", stdList);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbCurrencyOfName() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyDao.BbCurrencyOfName();
            json.put("BbCurrencyList", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject FbMoneySelectForBb(String userid, String currencyid) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = bbMoneyDao.BbCurrencyMoney(currencyid, userid);
            json.put("money", map);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}

