package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BbUsdtDao {
    public void BbUsdtInsert(Map<String, Object> map);

    public int BbUsdtSelectOfTixianCount(@Param("userid") String userid, @Param("map") Map<String, Object> map);

    public List<Map<String, Object>> BbUsdtSelectOfTixian(@Param("userid") String userid, @Param("map") Map<String, Object> map, @Param("page") int page,
                                                          @Param("num") int num);

    public List<Map<String, Object>> SubUsdtLog(@Param("userid") String userid, @Param("page") int page, @Param("num") int num);
}
