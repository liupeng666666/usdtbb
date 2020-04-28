package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbKlineDao;
import com.whp.usdtbb.other.Interface.BbKlineInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 14:41
 * @descrpition :
 */
@Service
public class BbKlineImpl implements BbKlineInterface {
    @Autowired
    private BbKlineDao bbKlineDao;

    @Override
    public JSONObject BbKlineRedis(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> fmap = bbKlineDao.getKlineSelect(map);
            if (fmap != null) {
                List<String> list = RedisUtils.lrange("kline." + fmap.get("pid"), Integer.parseInt(map.get("start").toString()), Integer.parseInt(map.get("num").toString()), 8);
                json.put("kline", list);
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
}
