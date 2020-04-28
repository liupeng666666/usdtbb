package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/28 13:41
 * @descrpition :
 */
@Mapper
public interface BbOrderDao {

    /**
     * @param pid
     * @return
     */
    public List<Map<String, Object>> getOrderSelect(@Param("pid") String pid);
}
