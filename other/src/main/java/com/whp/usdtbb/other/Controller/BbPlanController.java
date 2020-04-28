package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbPlanInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 18:03
 * @descrpition :
 */
@RestController
@RequestMapping("BbPlan")
public class BbPlanController {
    @Autowired
    private BbPlanInterface BbPlanInterface;

    @PostMapping("BbPlanInsert")
    public JSONObject BbPlanInsert(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = new JSONObject();
        map.put("userid", pid);
        System.out.println("开始：" + System.currentTimeMillis());
        json = BbPlanInterface.BbPlanInsert(map);
        System.out.println("结束：" + System.currentTimeMillis());
        return json;
    }

    @PostMapping("BbPlanSelect")
    public JSONObject BbPlanSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = new JSONObject();
        map.put("userid", pid);

        json = BbPlanInterface.BbPlanSelectUser(map);

        return json;
    }

    @PostMapping("BbPlanUpdate")
    public JSONObject BbPlanUpdate(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = BbPlanInterface.BbPlanUpdate(map);
        return json;
    }

    @PostMapping("BbPlanUpdateS")
    public JSONObject BbPlanUpdateS(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = BbPlanInterface.BbPlanUpdateS(map);
        return json;
    }


    @PostMapping("/BbPlanAppSelect")
    public JSONObject BbPlanAppSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = BbPlanInterface.BbPlanAppSelect(map);
        return json;
    }
}
