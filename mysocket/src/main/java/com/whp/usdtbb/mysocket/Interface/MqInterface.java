package com.whp.usdtbb.mysocket.Interface;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : 张吉伟
 * @data : 2019/6/16 14:17
 * @descrpition :
 */
public interface MqInterface {

    public JSONObject MqTask(JSONObject json);

    public JSONObject MqInsert(JSONObject json);

    public JSONObject MqRedis(JSONObject json);
}
