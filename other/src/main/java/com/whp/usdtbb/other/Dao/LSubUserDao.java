package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LSubUserDao {
    public int SubUserCount(Map<String, Object> map);
}
