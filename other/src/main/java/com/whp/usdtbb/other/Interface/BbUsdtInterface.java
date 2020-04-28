package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface BbUsdtInterface {
    public JSONObject BbUsdtInsert(Map<String, Object> map);

    public JSONObject BbUsdtSelectOfTixianBypage(String userid, Map<String, Object> map);

    public JSONObject SubUsdtLog(String userid, int page, int num);

}
