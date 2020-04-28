package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/27 10:36
 * @descrpition :
 */
@Mapper
public interface BbLoginLogDao {

    public List<Map<String, Object>> BbLoginLogSelect(@Param("userid") String userid, @Param("page") int page, @Param("num") int num);
}
