package com.whp.usdtbb.kline.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/15 19:03
 * @descrpition :
 */
@Mapper
public interface KlineBbKlineNewsDao {

    /**
     * @return
     */
    public List<Map<String, Object>> BbCurrencyNewsSelect();

    /**
     * @return
     */
    public List<Map<String, Object>> BbMinuteSelect();


    public List<Map<String, Object>> BbKlineNewsSelect();

    public void BbKlineNewsUpdate(Map<String, Object> map);
}
