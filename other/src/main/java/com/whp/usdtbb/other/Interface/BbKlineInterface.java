package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 14:41
 * @descrpition :
 */
public interface BbKlineInterface {
    public JSONObject BbKlineRedis(Map<String, Object> map);
}
