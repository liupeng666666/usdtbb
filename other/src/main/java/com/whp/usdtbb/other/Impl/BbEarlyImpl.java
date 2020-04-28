package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbEarlyDao;
import com.whp.usdtbb.other.Interface.BbEarlyInterface;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/17 19:09
 * @descrpition :
 */
@Service
public class BbEarlyImpl implements BbEarlyInterface {

    @Autowired
    private BbEarlyDao earlyDao;

    @Override
    public JSONObject BbEarlySelect() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = earlyDao.getEarlySelect();
            json.put("early", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbEarlyRecordInsert(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            earlyDao.BbEarlyRecordInsert(map);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbEarlyRecordSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = earlyDao.BbEarlyRecordSelect(map);
            json.put("early", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbEarlyRecordUpdate(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            earlyDao.BbEarlyRecordUpdate(map);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbEarlyRecordLogSelect(String userid, int page, int num) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = earlyDao.BbEarlyRecordLogSelect(userid, page, num);
            String cny = RedisUtils.get("CNY", 4);
            json.put("early", list);
            json.put("cny", cny);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
