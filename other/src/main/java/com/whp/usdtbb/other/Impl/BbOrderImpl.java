package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbOrderDao;
import com.whp.usdtbb.other.Interface.BbOrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 13:43
 * @descrpition :
 */
@Service
public class BbOrderImpl implements BbOrderInterface {
    @Autowired
    private BbOrderDao bbOrderDao;

    @Override
    public JSONObject BbOrderSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbOrderDao.getOrderSelect(map.get("pid").toString());
            json.put("code", 100);
            json.put("order", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
