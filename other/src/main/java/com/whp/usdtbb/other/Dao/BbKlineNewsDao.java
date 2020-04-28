package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BbKlineNewsDao {
    public String BbLineNewsSelectById(String currency_news_id);
}
