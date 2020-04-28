package com.whp.usdtbb.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 9:44
 * @descrpition :
 */
@Mapper
public interface SubUserDao {

    /**
     * @param pid
     * @return
     */
    public Map<String, Object> getSubUserByUser(@Param("pid") String pid);
}
