package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/17 19:07
 * @descrpition :
 */
@Mapper
public interface BbEarlyDao {

    public List<Map<String, Object>> getEarlySelect();

    public void BbEarlyRecordInsert(Map<String, Object> map);

    public List<Map<String, Object>> BbEarlyRecordSelect(Map<String, Object> map);

    public void BbEarlyRecordUpdate(Map<String, Object> map);


    public List<Map<String, Object>> BbEarlyRecordLogSelect(@Param("userid") String userid, @Param("page") int page, @Param("num") int num);
}
