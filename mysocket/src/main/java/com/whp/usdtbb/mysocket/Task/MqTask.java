package com.whp.usdtbb.mysocket.Task;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : 张吉伟
 * @data : 2019/6/16 15:49
 * @descrpition :
 */
@Component
public class MqTask {
    @Autowired
    private MqInterface mqInterface;

    //    @Scheduled(initialDelay = 1000, fixedRate = 5000)
//    public void task(){
//        JSONObject json=new JSONObject();
//        json.put("pid","6679bff2-8f4f-11e9-b3d0-0242ac110004");
//        json.put("money","1.45");
//        json.put("number",100);
//        json.put("volume",10);
//        json.put("message",json.toString());
//        json.put("queue","bb.aaa");
//        mqInterface.MqInsert(json);
//    }
//
//
    @Scheduled(initialDelay = 30000, fixedRate = 1111111111111L)
    public void task3() {
        JSONObject json = new JSONObject();
        json.put("queue", "bb.buy");
        mqInterface.MqTask(json);
    }

    @Scheduled(initialDelay = 30000, fixedRate = 1111111111111L)
    public void task2() {
        JSONObject json = new JSONObject();
        json.put("queue", "bb.sell");
        mqInterface.MqRedis(json);
    }

}
