package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbFreeDao;
import com.whp.usdtbb.other.Interface.BbFreeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BbFreeImpl implements BbFreeInterface {

    @Autowired
    private BbFreeDao bbFreeDao;

    @Override
    public JSONObject BbFreeInsert(String id, String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid == null) {
                json.put("code", 101);
            } else {
                bbFreeDao.BbFreeInsert(id, userid);
                json.put("code", 100);
            }


        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbFreeDel(String id, String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid == null) {
                json.put("code", 101);
            } else {
                bbFreeDao.BbFreeDel(id, userid);
                json.put("code", 100);
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbFreeSelectBySort() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbFreeDao.bbFreeSelectBySort();
            json.put("bbFreeList", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }


    @Override
    public JSONObject BbFreeUpdate(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            bbFreeDao.BbFreeUpdate(map);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
