package com.whp.usdtbb.other.Interface;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : 张吉伟
 * @data : 2019/7/27 10:54
 * @descrpition :
 */
public interface BbLoginLogInterface {

    public JSONObject BbLoginLogSelect(String userid, int page, int num);
}
