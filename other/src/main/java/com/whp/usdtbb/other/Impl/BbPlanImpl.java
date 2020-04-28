package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.other.Dao.BbMoneyDao;
import com.whp.usdtbb.other.Dao.BbPlanDao;
import com.whp.usdtbb.other.Dao.BbSellDao;
import com.whp.usdtbb.other.Interface.BbPlanInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 14:57
 * @descrpition :
 */
@Service
public class BbPlanImpl implements BbPlanInterface {
    @Autowired
    private BbCurrencyDao bbCurrencyDao;
    @Autowired
    private BbMoneyDao bbMoneyDao;
    @Autowired
    private BbPlanDao bbPlanDao;
    @Autowired
    private MqInterface mqInterface;
    @Autowired
    private BbSellDao bbSellDao;

    @Override
    public JSONObject BbPlanInsert(Map<String, Object> cmap) {
        JSONObject jsons = new JSONObject();
        try {
            JSONObject json = new JSONObject(cmap);

            Map<String, Object> map = bbCurrencyDao.getCurrencySelect(json.getString("currencyid"), json.getString("userid"));
            if (map != null) {
                if (!map.containsKey("left_surplus")) {
                    JSONObject cjson = new JSONObject();
                    cjson.put("userid", json.getString("userid"));
                    cjson.put("currencyid", map.get("currency_left"));
                    cjson.put("name", map.get("name").toString().split("/")[0]);
                    bbMoneyDao.BbMoneyInsert(cjson);
                    if (json.getInteger("type") == 1) {
                        jsons.put("code", 102);
                        return jsons;
                    }
                }
                if (!map.containsKey("right_surplus")) {
                    JSONObject cjson = new JSONObject();
                    cjson.put("userid", json.getString("userid"));
                    cjson.put("currencyid", map.get("currency_right"));
                    cjson.put("name", map.get("name").toString().split("/")[1]);
                    bbMoneyDao.BbMoneyInsert(cjson);
                    if (json.getInteger("type") == 0) {
                        jsons.put("code", 102);
                        return jsons;
                    }
                }
                JSONObject fjson = new JSONObject(map);
                if (json.getInteger("type") == 0) {
                    if (json.getBigDecimal("number").compareTo(fjson.getBigDecimal("right_surplus")) != 1 && json.getBigDecimal("number").compareTo(BigDecimal.ZERO) == 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("userid", json.getString("userid"));
                        cjson.put("currencyid", map.get("currency_right"));
                        cjson.put("number", json.getBigDecimal("number"));
                        bbMoneyDao.BbMoneyUpdate(cjson);
                    } else {
                        jsons.put("code", 102);
                        return jsons;
                    }
                } else if (json.getInteger("type") == 1) {
                    if (json.getBigDecimal("number").compareTo(fjson.getBigDecimal("left_surplus")) != 1 && json.getBigDecimal("number").compareTo(BigDecimal.ZERO) == 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("userid", json.getString("userid"));
                        cjson.put("currencyid", map.get("currency_left"));
                        cjson.put("number", json.getBigDecimal("number"));
                        bbMoneyDao.BbMoneyUpdate(cjson);
                    } else {
                        jsons.put("code", 102);
                        return jsons;
                    }
                }
                String pid = UUID.randomUUID().toString();
                json.put("pid", pid);
                bbPlanDao.BbPlanInsert(json);
                jsons.put("code", 100);

            } else {
                jsons.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsons.put("code", 103);
        }
        return jsons;
    }

    @Override
    public JSONObject BbPlanSelectUser(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbPlanDao.BbPlanSelect(map);
            json.put("plan", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbPlanUpdate(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> fmap = bbPlanDao.BbPlanUpdateDan(map);
            if (fmap != null && Integer.parseInt(fmap.get("state").toString()) != 2 && Integer.parseInt(fmap.get("state").toString()) != 3) {
                bbPlanDao.BbPlanUpdate(map);
                JSONObject fjson = new JSONObject(fmap);
                JSONObject cjson = new JSONObject();
                System.out.println("------" + fjson);
                BigDecimal number = fjson.getBigDecimal("volume").subtract(fjson.getBigDecimal("number"));
                if (fjson.getInteger("type") == 0) {

                    cjson.put("currencyid", fjson.get("currency_right"));
                } else {
                    cjson.put("currencyid", fjson.get("currency_left"));
                }
                cjson.put("number", number);
                cjson.put("userid", fjson.get("userid"));
                bbMoneyDao.BbMoneyUpdate(cjson);
                if (fjson.getInteger("trigger") == 1) {
                    map.put("pid", fjson.get("sell_pid"));
                    bbSellDao.BSellUpdate(map);
//                JSONObject zjson=new JSONObject();
//                zjson.put("currency_news_id",fjson.get("currency_news_id"));
//                zjson.put("type",fjson.get("type"));
//                zjson.put("un",fjson.getBigDecimal("univalent"));
//                Map<String,Object> sumMap=bbSellDao.BbSellSum(zjson);
//                if(sumMap!=null) {
//                    zjson = new JSONObject(sumMap);
//                    System.out.println(zjson);
//                    BigDecimal univalent = fjson.getBigDecimal("univalent").setScale(2, BigDecimal.ROUND_DOWN);
//                    zjson.put("univalent", univalent);
//                    Map<String, String> cmap = new HashMap<>();
//                    cmap.put(univalent + "", zjson.toString());
//                    RedisUtils.HMSET(fjson.get("currency_news_id") + "." + fjson.get("type"), cmap, 8);
//                    List<String> list = RedisUtils.lrange("list." + fjson.get("currency_news_id") + "-" + univalent + "." + fjson.get("type"), 0, -1, 8);
//
//                    for (String a : list) {
//                        JSONObject xjson = JSONObject.parseObject(a);
//
//                        if (xjson.containsKey("sellid") && xjson.getInteger("sellid").equals(fjson.getInteger("sellid"))) {
//                            RedisUtils.LREM("list." + fjson.get("currency_news_id") + "-" + univalent + "." + fjson.get("type"), 8, a);
//                            break;
//                        }
//
//                    }
//                }
                }
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbPlanUpdateS(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<String> list = bbPlanDao.BbPlanStateSelect(map);
            for (String a : list) {
                map.put("pid", a);
                BbPlanUpdate(map);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbPlanAppSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbPlanDao.BbPlanAppSelect(map, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));
            json.put("code", 100);
            json.put("plan", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
