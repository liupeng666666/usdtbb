package com.whp.usdtbb.kline.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.kline.Dao.KlineBbPlanDao;
import com.whp.usdtbb.kline.Interface.KlinePlanInterface;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : 张吉伟
 * @data : 2019/7/8 16:39
 * @descrpition :
 */
@Service
public class KlinePlanImpl implements KlinePlanInterface {
    @Autowired
    private KlineBbPlanDao klineBbPlanDao;
    @Autowired
    private MqInterface mqInterface;

    @Async
    @Override
    public void KlinePlanCl(Map<String, Object> map) {
        System.out.println(map.get("close"));
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> maps = klineBbPlanDao.BbPlanSelect(map);
            for (Map<String, Object> map1 : maps) {
                JSONObject cjson = new JSONObject();
                cjson.put("trigger", 1);
                cjson.put("un", map.get("close"));
                cjson.put("pid", map1.get("pid"));
                klineBbPlanDao.BbPlanUpdate(cjson);
                JSONObject zjson = new JSONObject();
                zjson.put("userid", map1.get("userid"));
                zjson.put("currencyid", map1.get("currency_news_id"));
                if (Float.parseFloat(map.get("close").toString()) >= Float.parseFloat(map1.get("max_sell").toString())) {
                    zjson.put("univalent", Float.parseFloat(map1.get("max_un").toString()));
                } else {
                    zjson.put("univalent", Float.parseFloat(map1.get("min_un").toString()));
                }
                String pid = UUID.randomUUID().toString();
                if (Integer.parseInt(map1.get("type").toString()) == 0) {
                    BigDecimal number = new BigDecimal(map1.get("number").toString()).divide(zjson.getBigDecimal("univalent"), 8, BigDecimal.ROUND_DOWN);
                    zjson.put("number", number);
                } else {
                    zjson.put("number", map1.get("number"));
                }

                zjson.put("type", map1.get("type"));
                zjson.put("plan", 1);
                zjson.put("planid", map1.get("pid"));
                zjson.put("pid", pid);
                klineBbPlanDao.KlineBbSellInsert(zjson);
                String sellid = klineBbPlanDao.BbSellid(pid);
                zjson.put("sellid", sellid);
                JSONObject sjson = new JSONObject();
                sjson.put("message", zjson);
                sjson.put("queue", "bb.sell");
                System.out.println(sjson);
                mqInterface.MqInsert(sjson);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
