package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbKlineNewsDao;
import com.whp.usdtbb.other.Interface.BbKlineNewsInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BbKlineNewsImpl implements BbKlineNewsInterface {
    @Autowired
    private BbKlineNewsDao bbKlineNewsDao;


    @Override
    public JSONObject BbLineNewsSelectById(String currency_news_id) {
        JSONObject json = new JSONObject();
        try {
            String pid = bbKlineNewsDao.BbLineNewsSelectById(currency_news_id);
            List<String> list = RedisUtils.lrange("kline." + pid, 0, 1440, 8);
            json.put("klinelist", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
