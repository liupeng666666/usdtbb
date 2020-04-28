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
public interface BbSellDao {

    public void BbSellInsert(Map<String, Object> map);

    public String BbSellid(@Param("pid") String pid);

    /**
     * @param map
     * @return
     */
    public List<Map<String, Object>> BbSellSelect(Map<String, Object> map);


    List<Map<String, Object>> BbSellaSelect(@Param("map") Map<String, Object> map, @Param("page") int page, @Param("num") int num);

    int BbSellaCount(@Param("map") Map<String, Object> map);


    List<Map<String, Object>> BbSellbSelect(@Param("map") Map<String, Object> map, @Param("page") int page, @Param("num") int num);


    int BbSellbCount(@Param("map") Map<String, Object> map);


    public Map<String, Object> BbSellUpdateDan(Map<String, Object> map);

    public void BSellUpdate(Map<String, Object> map);

    public Map<String, Object> BbSellSum(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    public List<String> BbSellStateSelect(Map<String, Object> map);

    public List<Map<String, Object>> BbSellAppSelect(@Param("map") Map<String, Object> map, @Param("page") int page, @Param("num") int num);

    public List<Map<String, Object>> BbSellAppSellSelect(@Param("map") Map<String, Object> map, @Param("page") int page, @Param("num") int num);

    public List<Map<String, Object>> BbSellRedisSelect(@Param("currencyid") String currencyid, @Param("type") int type);
}
