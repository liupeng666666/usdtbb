package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/17 19:08
 * @descrpition :
 */
public interface BbEarlyInterface {

    public JSONObject BbEarlySelect();

    public JSONObject BbEarlyRecordInsert(Map<String, Object> map);

    public JSONObject BbEarlyRecordSelect(Map<String, Object> map);

    public JSONObject BbEarlyRecordUpdate(Map<String, Object> map);

    public JSONObject BbEarlyRecordLogSelect(String userid, int page, int num);
}
