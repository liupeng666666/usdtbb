package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BbFreeDao {
    public void BbFreeInsert(@Param("id") String id, @Param("userid") String userid);

    public void BbFreeDel(@Param("id") String id, @Param("userid") String userid);

    public List<Map<String, Object>> bbFreeSelectBySort();

    public void BbFreeUpdate(Map<String, Object> map);
}
