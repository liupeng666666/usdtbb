package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface BbFreeInterface {
    public JSONObject BbFreeInsert(String id, String userid);

    public JSONObject BbFreeDel(String id, String userid);

    public JSONObject BbFreeSelectBySort();

    public JSONObject BbFreeUpdate(Map<String, Object> map);
}
