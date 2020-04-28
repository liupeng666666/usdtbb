package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 17:27
 * @descrpition :
 */
@Mapper
public interface BbMoneyDao {

    public void BbMoneyInsert(Map<String, Object> map);

    public void BbMoneyUpdate(Map<String, Object> map);


    //划转
    public Map<String, Object> BbMoneySelectHua(@Param("currencyid") String currencyid, @Param("userid") String userid, @Param("lock") String lock);

    public void BbMoneyUpdateHua(Map<String, Object> map);

    public void BbMoneyUpdateHuaAdd(@Param("map") Map<String, Object> map);

    public void BbMoneyInsertHua(@Param("map") Map<String, Object> map, @Param("userid") String userid);

    public Map<String, Object> FbMoneySelectForBb(@Param("currencyid") String currencyid, @Param("userid") String userid, @Param("lock") String lock);//查询法币表是否存在本次所转币种

    //划转
    public void BbMoneyInsertHua(@Param("currencyid") String currencyid, @Param("userid") String userid);

    public void FbMoneyUpdateHua(Map<String, Object> map);

    public Map<String, Object> BbCurrencyMoney(@Param("currencyid") String currencyid, @Param("userid") String userid);

}
