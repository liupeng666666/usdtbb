package com.whp.usdtbb.mysocket.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/19 16:28
 * @descrpition :
 */
@Mapper
public interface MqBbCurrencyDao {

    public Map<String, Object> getCurrencySelect(@Param("pid") String pid);
}
