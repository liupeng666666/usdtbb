package com.whp.usdtbb.kline.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.kline.Dao.KlineBbKlineNewsDao;
import com.whp.usdtbb.kline.Dao.KlineBbOrderDao;
import com.whp.usdtbb.kline.Interface.KlineCurrencyInterface;
import com.whp.usdtbb.kline.Interface.KlinePlanInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 19:36
 * @descrpition :
 */
@Service
public class KlineCurrencyImpl implements KlineCurrencyInterface {
    @Autowired
    private KlineBbKlineNewsDao klineBbKlineNewsDao;
    @Autowired
    private KlineBbOrderDao klineBbOrderDao;
    @Autowired
    private KlinePlanInterface klinePlanInterface;

    @Async
    @Override
    public void currency(Map<String, Object> map) {
        try {
            String value = RedisUtils.get(map.get("pid").toString(), 8);
            if (value == null || "".equals(value)) {
                RedisUtils.set(map.get("pid").toString(), new JSONObject(map).toString(), 8);
            } else {
                JSONObject json = JSONObject.parseObject(value);
                String createtime = json.getString("createtime");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long statetime = format.parse(createtime).getTime();
                long time = System.currentTimeMillis() - statetime;
                if (time > Integer.parseInt(map.get("minute").toString()) * 60 * 1000) {
                    JSONObject cjson = new JSONObject();
                    cjson.put("pid", map.get("currency_news_id"));
                    cjson.put("createtime", createtime);
                    Map<String, Object> fmap = klineBbOrderDao.BbOrdersSelect(cjson);
                    if (fmap != null) {
                        json.put("open", fmap.get("open"));
                        json.put("close", fmap.get("close"));
                        json.put("volume", fmap.get("volume"));
                        json.put("low", fmap.get("min"));
                        json.put("high", fmap.get("max"));
                    } else {
                        json.put("open", json.get("close"));
                        json.put("close", json.get("close"));
                        json.put("volume", 0);
                        json.put("low", json.get("close"));
                        json.put("high", json.get("close"));
                    }
                    klineBbKlineNewsDao.BbKlineNewsUpdate(json);
                    int num = 1000;
                    if (Integer.parseInt(map.get("minute").toString()) == 1) {
                        num = 5000;
                    }
                    RedisUtils.LPUSH("kline." + map.get("pid"), json.toString(), 8, num);
                    json.put("createtime", format.format(new Date()));
                    RedisUtils.set(map.get("pid").toString(), json.toString(), 8);
                    JSONObject zjson = new JSONObject();
                    zjson.put("close", json.get("close"));
                    zjson.put("pid", map.get("currency_news_id"));
                    System.out.println("-------" + zjson);
                    klinePlanInterface.KlinePlanCl(zjson);
                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
