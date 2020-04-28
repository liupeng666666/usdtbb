package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface LBbAssetOrederDao {
    //币币转现货 加减
    void LSubAssetAdd(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    void LSubAssetSub(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    void LFbAssetAdd(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    void LFbAssetSub(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    void LFbAssetInsert(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    void LFbAssetTransferIns(@Param("from_currency") String from_currency, @Param("to_currency") String to_currency,
                             @Param("from_money") BigDecimal from_money, @Param("to_money") BigDecimal to_money,
                             @Param("userid") String userid, @Param("type") String type);

    public Map<String, Object> LXhAssetSelect(@Param("userid") String userid);
}
