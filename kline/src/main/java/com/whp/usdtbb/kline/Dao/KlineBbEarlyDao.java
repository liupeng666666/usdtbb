package com.whp.usdtbb.kline.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/7/26 14:13
 * @descrpition :
 */
@Mapper
public interface KlineBbEarlyDao {

    /**
     * @return
     */
    public List<Map<String, Object>> BbEarlysSelect();

    public void BbEarlyUpdate(Map<String, Object> map);

    public Map<String, Object> BbMoneySelect(Map<String, Object> map);

    public void BbMoneyUpdate(Map<String, Object> map);

    public void BbEarlyInsert(Map<String, Object> map);
}
