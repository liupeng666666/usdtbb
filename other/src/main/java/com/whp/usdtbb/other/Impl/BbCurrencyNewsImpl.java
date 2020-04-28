package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.other.Dao.BbCurrencyNewsDao;
import com.whp.usdtbb.other.Interface.BbCurrencyNewsInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class BbCurrencyNewsImpl implements BbCurrencyNewsInterface {
    @Autowired
    private BbCurrencyNewsDao bbCurrencyNewsDao;
    @Autowired
    private BbCurrencyDao bbCurrencyDao;

    @Override
    public JSONObject getBbCurrencyNewsSelect() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyNewsDao.getBbCurrencyNewsSelect();
            String cny = RedisUtils.get("CNY", 4);
            json.put("bbCurrencyNews", list);
            json.put("cny", cny);
            json.put("code", 100);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbCurrencyNewsSelect(String pid, String userid) {
        JSONObject json = new JSONObject();
        try {
            long time = System.currentTimeMillis();
            long left_open = 0;
            long right_open = 0;
            boolean status = true;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = bbCurrencyDao.BbCurrencyNewsSelect(pid, userid);
            if (map.get("left_open").toString().equals("0")) {
                left_open = simpleDateFormat.parse(map.get("left_opentime").toString()).getTime();
                status = false;
                if (left_open - time < 0) {
                    bbCurrencyDao.BbCurrencyUpdate(map.get("currency_left").toString());
                }
            } else {
                left_open = time;
            }
            if (map.get("right_open").toString().equals("0")) {
                right_open = simpleDateFormat.parse(map.get("right_opentime").toString()).getTime();
                status = false;
                if (right_open - time < 0) {
                    bbCurrencyDao.BbCurrencyUpdate(map.get("currency_right").toString());
                }
            } else {
                right_open = time;
            }
            System.out.println(status);
            if (!status) {
                if ((left_open - time) > (right_open - time)) {
                    json.put("date", (left_open - time));
                    json.put("datetime", map.get("left_opentime"));
                } else {
                    json.put("date", (right_open - time));
                    json.put("datetime", map.get("right_opentime"));
                }
            } else {
                Map<String, Object> map1 = bbCurrencyDao.CurrencyUsdt(map.get("currency_right").toString());
                if (map1 != null && map1.containsKey("money")) {
                    json.put("money", map1.get("money"));

                } else {
                    json.put("money", 1);
                }
                json.put("cny", RedisUtils.get("CNY", 4));
            }
            json.put("status", status);

            json.put("currency", map);
            json.put("code", 100);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getBbCurrencyNewsSelectOption(@RequestParam String currency_right, @RequestParam String userid, @RequestParam String currency_news_id, @RequestParam String name, @RequestParam String sort) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyNewsDao.getBbCurrencyNewsSelectOptional(currency_right, userid, currency_news_id, name, sort);
            List<Map<String, Object>> usdtList = bbCurrencyNewsDao.getBbCurrencyNewsSelect();
            String cny = RedisUtils.get("CNY", 4);
            json.put("bbCurrencyNewsOptional", list);
            json.put("usdtList", usdtList);
            json.put("cny", cny);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getBbCurrencyNewsSelectByStyle() {
        JSONObject json = new JSONObject();
        try {
            String cny = RedisUtils.get("CNY", 4);
            List<Map<String, Object>> list = bbCurrencyNewsDao.getBbCurrencyNewsSelectByStyle();
            json.put("bbCurrencyNews", list);
            json.put("cny", cny);
            json.put("code", 100);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbCurrencyNewsMoney(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> ma = bbCurrencyDao.getCurrencySelect(map.get("pid").toString(), map.get("userid").toString());
            json.put("currency", ma);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbCurrencyNewsSort(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            map.put("state", 0);
            List<Map<String, Object>> list1 = bbCurrencyNewsDao.BbCurrencyNewsSort(map);
            map.put("state", 1);
            List<Map<String, Object>> list2 = bbCurrencyNewsDao.BbCurrencyNewsSort(map);
            map.put("state", 2);
            List<Map<String, Object>> list3 = bbCurrencyNewsDao.BbCurrencyNewsSort(map);
            json.put("list1", list1);
            json.put("list2", list2);
            json.put("list3", list3);
            json.put("CNY", RedisUtils.get("CNY", 4));
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbCurrencyNewsQB() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbCurrencyNewsDao.BbCurrencyNewsQB();
            json.put("currency", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

}
