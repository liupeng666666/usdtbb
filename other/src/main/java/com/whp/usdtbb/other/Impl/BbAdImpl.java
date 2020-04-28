package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbAdDao;
import com.whp.usdtbb.other.Interface.BbAdInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 17:48
 * @descrpition :
 */
@Service
public class BbAdImpl implements BbAdInterface {
    @Autowired
    private BbAdDao bbAdDao;

    @Override
    public JSONObject getAdSelect() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbAdDao.getAdSelect();
            json.put("ad", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
