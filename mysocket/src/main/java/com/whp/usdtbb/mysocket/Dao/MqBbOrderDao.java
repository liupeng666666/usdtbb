package com.whp.usdtbb.mysocket.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/19 15:50
 * @descrpition :
 */
@Mapper
public interface MqBbOrderDao {

    public void BbOrderInsert(Map<String, Object> map);
}
