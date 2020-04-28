package com.whp.usdtbb.kline.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.kline.Dao.KlineBbEarlyDao;
import com.whp.usdtbb.kline.Interface.KlineBbEarlyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/26 14:17
 * @descrpition :
 */
@Service
public class KlineBbEarlyImpl implements KlineBbEarlyInterface {

    @Autowired
    private KlineBbEarlyDao klineBbEarlyDao;

    @Async
    @Override
    public JSONObject KlineBbEarly() {
        try {
            List<Map<String, Object>> list = klineBbEarlyDao.BbEarlysSelect();
            for (Map<String, Object> map : list) {
                JSONObject json = new JSONObject(map);
                if (json.getBigDecimal("money").compareTo(json.getBigDecimal("max")) > -1 || json.getBigDecimal("money").compareTo(json.getBigDecimal("min")) < 1) {
                    int state = 2;
                    if (json.getInteger("state") == 0) {

                        if (json.getInteger("minute") != 0) {

                            state = 1;
                        }

                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        long update = simpleDateFormat.parse(json.getString("updatetime")).getTime();
                        long time = update + json.getInteger("minute") * 60 * 1000;
                        long end_time = System.currentTimeMillis();
                        System.out.println(time + "==" + end_time);
                        if (time <= end_time) {
                            state = 1;
                        } else {
                            continue;
                        }
                    }
                    Map<String, Object> fmap = klineBbEarlyDao.BbMoneySelect(map);
                    if (fmap != null) {
                        JSONObject fjson = new JSONObject(fmap);
                        if (fjson.getBigDecimal("surplus").compareTo(json.getBigDecimal("sxf")) > -1) {
                            JSONObject cjson = new JSONObject();
                            cjson.put("currencyid", json.get("currencyid"));
                            cjson.put("userid", json.get("userid"));
                            cjson.put("sxf", json.get("sxf"));
                            klineBbEarlyDao.BbMoneySelect(cjson);
                            cjson = new JSONObject();
                            cjson.put("pid", json.get("pid"));
                            cjson.put("state", state);
                            cjson.put("num", 1);
                            klineBbEarlyDao.BbEarlyUpdate(cjson);
                            cjson = new JSONObject();
                            if (json.getBigDecimal("money").compareTo(json.getBigDecimal("max")) > -1) {
                                cjson.put("bfb", 0);
                            } else {
                                cjson.put("bfb", 1);
                            }
                            cjson.put("bb_early_record", json.get("pid"));
                            cjson.put("userid", json.get("userid"));
                            cjson.put("money", json.get("money"));
                            klineBbEarlyDao.BbEarlyInsert(cjson);


                            //发送短信
                        }
                    }
                } else {
                    if (json.getInteger("state") == 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("pid", json.get("pid"));
                        cjson.put("state", 2);
                        klineBbEarlyDao.BbEarlyUpdate(cjson);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
