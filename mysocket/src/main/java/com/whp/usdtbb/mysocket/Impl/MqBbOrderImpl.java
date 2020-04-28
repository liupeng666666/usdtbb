package com.whp.usdtbb.mysocket.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.mysocket.Dao.MqBbOrderDao;
import com.whp.usdtbb.mysocket.Dao.MqBbCurrencyDao;
import com.whp.usdtbb.mysocket.Dao.MqBbMoneyDao;
import com.whp.usdtbb.mysocket.Dao.MqBbSellDao;
import com.whp.usdtbb.mysocket.Interface.MqBbOrderInterface;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/19 11:49
 * @descrpition :
 */
@Service
public class MqBbOrderImpl implements MqBbOrderInterface {

    @Autowired
    private MqBbOrderDao bbOrderDao;
    @Autowired
    private MqBbCurrencyDao mqBbCurrencyDao;
    @Autowired
    private MqBbMoneyDao mqBbMoneyDao;
    @Autowired
    private MqBbSellDao mqBbSellDao;
    @Autowired
    private MqInterface mqInterface;

    @Override
    public boolean MqBbOrder(JSONObject json) {

        System.out.println("---json-----:" + json);
        boolean status = false;
        try {
            String currencyid = json.getString("currencyid");
            BigDecimal univalent = json.getBigDecimal("univalent").setScale(8, BigDecimal.ROUND_DOWN);
            String type = json.getString("type");
            String ftype = Math.abs(1 - json.getInteger("type")) + "";
            BigDecimal number = json.getBigDecimal("number");
            Map<String, Object> value = mqBbSellDao.BbSellSelect(currencyid, ftype, univalent);
            if (value == null) {

            } else {
                JSONObject fjson = new JSONObject(value);
                BigDecimal surplus = fjson.getBigDecimal("number").subtract(fjson.getBigDecimal("volume"));
                List<Map<String, Object>> list = mqBbSellDao.BbSellSelectZ(currencyid, ftype, univalent);
                BigDecimal money = number;
                if (surplus.compareTo(number) > -1) {
                    System.out.println("进入number处理");
                    //采用number计算
                    for (Map<String, Object> fv : list) {
                        if (number.compareTo(BigDecimal.ZERO) == 0) {
                            break;
                        }
                        JSONObject fvjson = new JSONObject(fv);
                        BigDecimal fv_surplus = fvjson.getBigDecimal("number").subtract(fvjson.getBigDecimal("volume"));
                        if (fv_surplus.compareTo(number) > 0) {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, number, fvjson.getString("sellid"), 1, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, number, json.getString("sellid"), 2, type);
                            }
                            fvjson.put("volume", fvjson.getBigDecimal("volume").add(number));
                            // RedisUtils.LSET("list." + currencyid + "-" + univalent + "." + ftype, 0, fvjson.toString(), 8);
                            number = BigDecimal.ZERO;
                        } else if (fv_surplus.compareTo(number) == 0) {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, number, fvjson.getString("sellid"), 3, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, number, json.getString("sellid"), 3, type);
                            }
                            // RedisUtils.LPOP("list." + currencyid + "-" + univalent + "." + ftype, 8);
                            number = BigDecimal.ZERO;
                        } else {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, fv_surplus, fvjson.getString("sellid"), 2, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, fv_surplus, json.getString("sellid"), 1, type);
                            }
                            number = number.subtract(fv_surplus);
                        }
                    }
                } else {
                    System.out.println("进入surplus处理");
                    //采用surplus计算
                    for (Map<String, Object> fv : list) {
                        JSONObject fvjson = new JSONObject(fv);
                        BigDecimal fv_surplus = fvjson.getBigDecimal("number").subtract(fvjson.getBigDecimal("volume"));
                        if (number.compareTo(BigDecimal.ZERO) == 0) {
                            continue;
                        }
                        if (fv_surplus.compareTo(BigDecimal.ZERO) == 0) {
                            continue;
                        }
                        if (fv_surplus.compareTo(number) > 0) {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, number, fvjson.getString("sellid"), 1, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, number, json.getString("sellid"), 2, type);
                            }
                            number = BigDecimal.ZERO;
                        } else if (fv_surplus.compareTo(number) == 0) {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, number, fvjson.getString("sellid"), 3, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, number, json.getString("sellid"), 3, type);
                            }
                            number = BigDecimal.ZERO;
                        } else {

                            if ("0".equals(type)) {
                                Fv_order(json.getString("userid"), fvjson.getString("userid"), currencyid, json.getString("sellid"), univalent, fv_surplus, fvjson.getString("sellid"), 2, type);
                            } else {
                                Fv_order(fvjson.getString("userid"), json.getString("userid"), currencyid, fvjson.getString("sellid"), univalent, fv_surplus, json.getString("sellid"), 1, type);
                            }
                            number = number.subtract(fv_surplus);
                        }
                    }
                }
            }
            Map<String, Object> map = mqBbSellDao.BbSellSelect(currencyid, type, univalent);
            mq(currencyid, univalent, map, type);
            Map<String, Object> map1 = mqBbSellDao.BbSellSelect(currencyid, ftype, univalent);
            mq(currencyid, univalent, map1, ftype);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public void mq(String currencyid, BigDecimal univalent, Map<String, Object> map, String type) {
        JSONObject zjson = new JSONObject();
        zjson.put("pid", currencyid);
        zjson.put("money", univalent);
        zjson.put("number", map.get("number"));
        zjson.put("volume", map.get("volume"));
        zjson.put("type", type);
        zjson.put("code", 201);
        zjson.put("message", zjson.toString());
        zjson.put("queue", "bb.buy");
        mqInterface.MqInsert(zjson);
    }

    public boolean Fv_order(String userid, String fuserid, String currencyid, String sellid, BigDecimal univalent, BigDecimal number, String fsellid, int state, String type) {
        System.out.println("----------------------");
        System.out.println("当前数量：" + number);
        System.out.println("----------------------");
        boolean status = true;
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> map = mqBbCurrencyDao.getCurrencySelect(currencyid);
            JSONObject fmap = new JSONObject(map);
            BigDecimal countermark = number.multiply(univalent);
            BigDecimal left_sxf = number.multiply(fmap.getBigDecimal("poundage_left"));
            BigDecimal right_sxf = countermark.multiply(fmap.getBigDecimal("poundage_right"));
            //购买方记录
            json.put("userid", userid);
            json.put("fuserid", fuserid);
            json.put("currency_news_id", currencyid);
            json.put("sellid", sellid);
            json.put("univalent", univalent);
            json.put("number", number);
            json.put("poundage", left_sxf);
            json.put("countermark", countermark);
            json.put("type", 0);
            if ("0".equals(type)) {
                json.put("style", 1);
            } else {
                json.put("style", 0);
            }
            bbOrderDao.BbOrderInsert(json);
            JSONObject fmoney = new JSONObject();
            fmoney.put("userid", userid);
            fmoney.put("number", number.subtract(left_sxf));
            fmoney.put("currencyid", fmap.getString("currency_left"));
            mqBbMoneyDao.BbMoneyUpdateSurplus(fmoney);
//            fmoney.put("number", countermark);
//            fmoney.put("currencyid", fmap.getString("currency_right"));
//            mqBbMoneyDao.BbMoneyUpdateSurplus(fmoney);
            JSONObject csell = new JSONObject();
            if (state == 1 || state == 3) {
                csell.put("state", 2);
            } else {
                csell.put("state", 1);
            }
            csell.put("volume", number);
            csell.put("sellid", sellid);
            mqBbSellDao.BbSellUpdate(csell);

            //出售方记录

            json.put("sellid", fsellid);
            json.put("poundage", right_sxf);
            json.put("type", 1);
            if ("0".equals(type)) {
                json.put("style", 0);
            } else {
                json.put("style", 1);
            }
            bbOrderDao.BbOrderInsert(json);

            fmoney.put("userid", fuserid);
            fmoney.put("number", countermark.subtract(left_sxf));
            fmoney.put("currencyid", fmap.getString("currency_right"));
            mqBbMoneyDao.BbMoneyUpdateSurplus(fmoney);
//            fmoney.put("number", number);
//            fmoney.put("currencyid", fmap.getString("currency_left"));
//            mqBbMoneyDao.BbMoneyUpdateSurplus(fmoney);
            if (state == 2 || state == 3) {
                csell.put("state", 2);
            } else {
                csell.put("state", 1);
            }
            csell.put("volume", number);
            csell.put("sellid", fsellid);
            mqBbSellDao.BbSellUpdate(csell);

            Map<String, Object> cmap = mqBbCurrencyDao.getCurrencySelect(currencyid);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            JSONObject zjson = new JSONObject();
            zjson.put("univalent", univalent);
            zjson.put("number", number);
            zjson.put("time", format.format(new Date()));
            zjson.put("type", 0);
            zjson.put("code", 202);
            zjson.put("bfb", cmap.get("bfb"));
            zjson.put("min", cmap.get("min"));
            zjson.put("max", cmap.get("max"));
            zjson.put("volume", cmap.get("volume"));
            zjson.put("character", cmap.get("character"));
            zjson.put("message", zjson.toString());
            zjson.put("queue", "bb.buy");

            JSONObject sjson = new JSONObject();
            sjson.put("univalent", univalent);
            sjson.put("number", number);
            sjson.put("time", format.format(new Date()));
            sjson.put("type", 1);
            sjson.put("code", 202);
            sjson.put("bfb", cmap.get("bfb"));
            sjson.put("min", cmap.get("min"));
            sjson.put("max", cmap.get("max"));
            sjson.put("volume", cmap.get("volume"));
            sjson.put("character", cmap.get("character"));
            sjson.put("message", sjson.toString());
            sjson.put("queue", "bb.buy");

            if ("0".equals(type)) {
                mqInterface.MqInsert(zjson);
            } else {
                mqInterface.MqInsert(sjson);
            }
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }
}
