package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 13:42
 * @descrpition :
 */
public interface BbOrderInterface {
    public JSONObject BbOrderSelect(Map<String, Object> map);
}
