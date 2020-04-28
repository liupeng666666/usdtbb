package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BbTransferDao {
    public List<Map<String, Object>> BbTransferDetailSelect(@Param("map") Map<String, Object> map, @Param("userid") String userid);
}
