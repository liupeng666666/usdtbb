package com.whp.usdtbb.kline.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/8 16:50
 * @descrpition :
 */
@Mapper
public interface KlineBbPlanDao {

    public List<Map<String, Object>> BbPlanSelect(Map<String, Object> map);

    public void KlineBbSellInsert(Map<String, Object> map);

    public void BbPlanUpdate(Map<String, Object> map);

    public String BbSellid(@Param("pid") String pid);
}
