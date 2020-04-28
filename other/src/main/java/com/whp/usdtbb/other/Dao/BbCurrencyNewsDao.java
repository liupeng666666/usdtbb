package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BbCurrencyNewsDao {
    public List<Map<String, Object>> getBbCurrencyNewsSelect();

    public List<Map<String, Object>> getBbCurrencyNewsSelectOptional(@Param("currency_right") String currency_right, @Param("userid") String userid, @Param("currency_news_id") String currency_news_id, @Param("name") String name, @Param("sort") String sort);

    public List<Map<String, Object>> getBbCurrencyNewsSelectByRight();

    public List<Map<String, Object>> getBbCurrencyNewsStd();

    public Map<String, Object> BbCurrencySort();

    public List<Map<String, Object>> getBbCurrencyNewsSelectByStyle();

    public Map<String, Object> BbCurrencyDan(@Param("pid") String pid);

    public List<Map<String, Object>> BbCurrencyNewsSort(Map<String, Object> map);

    public List<Map<String, Object>> BbCurrencyNewsQB();

}
