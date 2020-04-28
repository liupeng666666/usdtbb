package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.other.Dao.BbCurrencyNewsDao;
import com.whp.usdtbb.other.Dao.BbMoneyDao;
import com.whp.usdtbb.other.Dao.BbSellDao;
import com.whp.usdtbb.other.Interface.BbSellInterface;
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
public class BbSellImpl implements BbSellInterface {
    @Autowired
    private BbCurrencyDao bbCurrencyDao;
    @Autowired
    private BbMoneyDao bbMoneyDao;
    @Autowired
    private BbSellDao bbSellDao;
    @Autowired
    private MqInterface mqInterface;
    @Autowired
    private BbCurrencyNewsDao bbCurrencyNewsDao;

    @Override
    public JSONObject BbSellInsert(Map<String, Object> cmap) {
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
                    BigDecimal number = json.getBigDecimal("number").multiply(json.getBigDecimal("univalent"));
                    if (number.compareTo(fjson.getBigDecimal("right_surplus")) != 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("userid", json.getString("userid"));
                        cjson.put("currencyid", map.get("currency_right"));
                        cjson.put("number", number);
                        bbMoneyDao.BbMoneyUpdate(cjson);
                    } else {
                        jsons.put("code", 102);
                        return jsons;
                    }
                } else if (json.getInteger("type") == 1) {
                    BigDecimal number = json.getBigDecimal("number");
                    if (number.compareTo(fjson.getBigDecimal("left_surplus")) != 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("userid", json.getString("userid"));
                        cjson.put("currencyid", map.get("currency_left"));
                        cjson.put("number", number);
                        bbMoneyDao.BbMoneyUpdate(cjson);
                    } else {
                        jsons.put("code", 102);
                        return jsons;
                    }
                }
                String pid = UUID.randomUUID().toString();
                json.put("pid", pid);
                bbSellDao.BbSellInsert(json);
                String sellid = bbSellDao.BbSellid(pid);
                json.put("sellid", sellid);
                JSONObject cjson = new JSONObject();
                cjson.put("message", json);
                cjson.put("queue", "bb.sell");
                System.out.println("==c==" + cjson);
                mqInterface.MqInsert(cjson);
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
    public JSONObject BBSellSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            //List<String> list= RedisUtils.HVALS(map.get("pid").toString()+".0",8);
            // List<String> list1= RedisUtils.HVALS(map.get("pid").toString()+".1",8);
            List<Map<String, Object>> list = bbSellDao.BbSellRedisSelect(map.get("pid").toString(), 0);
            List<Map<String, Object>> list1 = bbSellDao.BbSellRedisSelect(map.get("pid").toString(), 1);
            json.put("buy", list);
            json.put("sell", list1);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbSellSelectUser(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbSellDao.BbSellSelect(map);
            json.put("sell", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbSellaSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> fmap = new HashMap<>();
            if (!map.containsKey("pid")) {
                fmap = bbCurrencyNewsDao.BbCurrencySort();
                map.put("pid", fmap.get("pid"));
            } else {
                fmap = bbCurrencyNewsDao.BbCurrencyDan(map.get("pid").toString());
            }
            List<Map<String, Object>> list = bbSellDao.BbSellaSelect(map, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));
            int count = bbSellDao.BbSellaCount(map);
            json.put("code", 100);
            json.put("sell", list);
            json.put("count", count);
            json.put("currency", fmap);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }


    @Override
    public JSONObject BbSellbSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> bmap = new HashMap<>();
            if (!map.containsKey("pid")) {
                bmap = bbCurrencyNewsDao.BbCurrencySort();
                map.put("pid", bmap.get("pid"));
            } else {
                bmap = bbCurrencyNewsDao.BbCurrencyDan(map.get("pid").toString());
            }
            List<Map<String, Object>> list = bbSellDao.BbSellbSelect(map, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));
            int count = bbSellDao.BbSellaCount(map);
            json.put("code", 100);
            json.put("plan", list);
            json.put("count", count);
            json.put("currency", bmap);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbSellUpdate(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> fmap = bbSellDao.BbSellUpdateDan(map);
            if (fmap != null) {
                bbSellDao.BSellUpdate(map);
                JSONObject fjson = new JSONObject(fmap);
                JSONObject cjson = new JSONObject();
                BigDecimal number = fjson.getBigDecimal("volume").subtract(fjson.getBigDecimal("number"));
                if (fjson.getInteger("type") == 0) {

                    cjson.put("currencyid", fjson.get("currency_right"));
                    cjson.put("number", number.multiply(fjson.getBigDecimal("univalent")));
                } else {
                    cjson.put("currencyid", fjson.get("currency_left"));
                    cjson.put("number", number);
                }

                cjson.put("userid", fjson.get("userid"));
                bbMoneyDao.BbMoneyUpdate(cjson);
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
    public JSONObject BbSellUpdateS(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<String> list = bbSellDao.BbSellStateSelect(map);
            for (String a : list) {
                map.put("pid", a);
                BbSellUpdate(map);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbSellAppSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbSellDao.BbSellAppSelect(map, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));
            json.put("code", 100);
            json.put("sell", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbSellAppSellSelect(Map<String, Object> map) {

        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbSellDao.BbSellAppSellSelect(map, Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));
            json.put("code", 100);
            json.put("sell", list);
            System.out.println("---------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }


}
