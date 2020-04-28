package com.whp.usdtbb.kline.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 16:40
 * @descrpition :
 */
@Mapper
public interface KlineBbOrderDao {

    /**
     * @param map
     * @return
     */
    public Map<String, Object> BbOrdersSelect(Map<String, Object> map);
}
