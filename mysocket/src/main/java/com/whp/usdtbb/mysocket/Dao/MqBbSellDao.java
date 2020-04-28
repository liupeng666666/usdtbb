package com.whp.usdtbb.mysocket.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/19 17:20
 * @descrpition :
 */
@Mapper
public interface MqBbSellDao {

    /**
     * @param map
     */
    public void BbSellUpdate(Map<String, Object> map);

    public Map<String, Object> BbSellSelect(@Param("currencyid") String currencyid, @Param("type") String type, @Param("univalent") BigDecimal univalent);

    public List<Map<String, Object>> BbSellSelectZ(@Param("currencyid") String currencyid, @Param("type") String type, @Param("univalent") BigDecimal univalent);
}
