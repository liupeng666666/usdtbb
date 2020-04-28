package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface BbSysWalletDao {
    public Map<String, Object> BbSysWalletSelect(@Param("codeid") String currencyid);
}
