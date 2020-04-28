package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 15:42
 * @descrpition :
 */
@Mapper
public interface BbPlanDao {

    public void BbPlanInsert(Map<String, Object> map);

    public String BbPlanid(@Param("pid") String pid);

    /**
     * @param map
     * @return
     */
    public List<Map<String, Object>> BbPlanSelect(Map<String, Object> map);

    public Map<String, Object> BbPlanUpdateDan(Map<String, Object> map);

    public void BbPlanUpdate(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    public List<String> BbPlanStateSelect(Map<String, Object> map);


    public List<Map<String, Object>> BbPlanAppSelect(@Param("map") Map<String, Object> map, @Param("page") int page, @Param("num") int num);
}
