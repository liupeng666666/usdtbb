package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbLoginLogDao;
import com.whp.usdtbb.other.Interface.BbLoginLogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/27 10:54
 * @descrpition :
 */
@Service
public class BbLoginLogImpl implements BbLoginLogInterface {
    @Autowired
    private BbLoginLogDao bbLoginLogDao;

    @Override
    public JSONObject BbLoginLogSelect(String userid, int page, int num) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbLoginLogDao.BbLoginLogSelect(userid, page, num);
            json.put("code", 100);
            json.put("log", list);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
