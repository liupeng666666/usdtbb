package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 14:57
 * @descrpition :
 */
public interface BbPlanInterface {

    public JSONObject BbPlanInsert(Map<String, Object> json);


    /**
     * @param map
     * @return
     */
    public JSONObject BbPlanSelectUser(Map<String, Object> map);

    public JSONObject BbPlanUpdate(Map<String, Object> map);

    public JSONObject BbPlanUpdateS(Map<String, Object> map);

    public JSONObject BbPlanAppSelect(Map<String, Object> map);
}
