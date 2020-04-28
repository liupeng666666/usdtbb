package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface BbTransferInterface {
    //币币划转 将数据插入fb_划转表
    public JSONObject FbTransferInsert(Map<String, Object> map, String userid);

    public JSONObject BbTransferDetailSelect(Map<String, Object> map, String userid);
}
