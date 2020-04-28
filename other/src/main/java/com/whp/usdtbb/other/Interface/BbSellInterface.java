package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 14:57
 * @descrpition :
 */
public interface BbSellInterface {

    public JSONObject BbSellInsert(Map<String, Object> json);

    public JSONObject BBSellSelect(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    public JSONObject BbSellSelectUser(Map<String, Object> map);

    JSONObject BbSellaSelect(Map<String, Object> map);

    JSONObject BbSellbSelect(Map<String, Object> map);


    public JSONObject BbSellUpdate(Map<String, Object> map);

    public JSONObject BbSellUpdateS(Map<String, Object> map);

    public JSONObject BbSellAppSelect(Map<String, Object> map);

    public JSONObject BbSellAppSellSelect(Map<String, Object> map);
}
