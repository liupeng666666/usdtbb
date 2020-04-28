package com.whp.usdtbb.kline.task;

import com.whp.usdtbb.kline.Dao.KlineBbKlineNewsDao;
import com.whp.usdtbb.kline.Interface.KlineBbEarlyInterface;
import com.whp.usdtbb.kline.Interface.KlineCurrencyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 19:34
 * @descrpition :
 */
@Component
public class KlineCurrencyTask {

    @Autowired
    private KlineBbKlineNewsDao klineBbKlineNewsDao;
    @Autowired
    private KlineCurrencyInterface klineCurrencyInterface;
    @Autowired
    private KlineBbEarlyInterface klineBbEarlyInterface;

    //    @Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void Task() {
        List<Map<String, Object>> list = klineBbKlineNewsDao.BbKlineNewsSelect();
        for (Map<String, Object> map : list) {
            klineCurrencyInterface.currency(map);
        }
        klineBbEarlyInterface.KlineBbEarly();
    }
}
