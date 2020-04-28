package com.whp.usdtbb.other.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 17:21
 * @descrpition :
 */
@Mapper
public interface BbCurrencyDao {

    public Map<String, Object> getCurrencySelect(@Param("pid") String pid, @Param("userid") String userid);

    /**
     * @param pid
     * @return
     */
    public Map<String, Object> BbCurrencyNewsSelect(@Param("pid") String pid, @Param("userid") String userid);


    public void BbCurrencyUpdate(@Param("pid") String pid);

    /**
     * @param left
     */
    public Map<String, Object> CurrencyUsdt(@Param("left") String left);

    public List<Map<String, Object>> currencySelectByStyle();//查询置顶的币币

    public Map<String, Object> BbCurrencyById(@Param("pid") String currencyid);

    public List<Map<String, Object>> BbCurrencyDealSelect(@Param("userid") String userid);//币币交易账户

    //划转
    public Map<String, Object> BbCurrencyHua(@Param("name") String name);

    public Map<String, Object> FbCurrencySelectByName(@Param("name") String name);//通过传的名字查询法币表

    //币币名称查询
    public List<Map<String, Object>> BbCurrencyOfName();

}
