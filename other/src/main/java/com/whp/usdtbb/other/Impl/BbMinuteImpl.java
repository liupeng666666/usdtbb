package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbMinuteDao;
import com.whp.usdtbb.other.Interface.BbMinuteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 9:35
 * @descrpition :
 */
@Service
public class BbMinuteImpl implements BbMinuteInterface {
    @Autowired
    private BbMinuteDao bbMinuteDao;

    @Override
    public JSONObject BbMinuteSelect() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbMinuteDao.getMinuteSelect();
            json.put("minute", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }
}
