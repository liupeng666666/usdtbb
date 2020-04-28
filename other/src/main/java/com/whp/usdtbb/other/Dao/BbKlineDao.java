package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 14:59
 * @descrpition :
 */
@Mapper
public interface BbKlineDao {
    /**
     * @param map
     * @return
     */
    public Map<String, Object> getKlineSelect(Map<String, Object> map);
}
