package com.whp.usdtbb.mysocket.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/19 16:43
 * @descrpition :
 */
@Mapper
public interface MqBbMoneyDao {

    /**
     * @param map
     */
    public void BbMoneyUpdateSurplus(Map<String, Object> map);

    /**
     * @param map
     */
    public void BbMoneyUpdateFrozen(Map<String, Object> map);
}
