package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 17:47
 * @descrpition :
 */
@Mapper
public interface BbAdDao {
    public List<Map<String, Object>> getAdSelect();
}
