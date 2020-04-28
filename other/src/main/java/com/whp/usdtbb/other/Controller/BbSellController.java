package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbSellInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/17 18:03
 * @descrpition :
 */
@RestController
@RequestMapping("BbSell")
public class BbSellController {
    @Autowired
    private BbSellInterface bbSellInterface;


    @PostMapping("BbSellInsert")
    public JSONObject BbSellInsert(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = new JSONObject();
        map.put("userid", pid);
        System.out.println("开始：" + System.currentTimeMillis());
        json = bbSellInterface.BbSellInsert(map);
        System.out.println("结束：" + System.currentTimeMillis());
        return json;
    }

    @PostMapping("BbSellSelect")
    public JSONObject BbSellSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = new JSONObject();
        map.put("userid", pid);

        json = bbSellInterface.BbSellSelectUser(map);

        return json;
    }

    @PostMapping("BbSellUpdate")
    public JSONObject BbSellUpdate(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellUpdate(map);
        return json;
    }

    @PostMapping("BbSellUpdateS")
    public JSONObject BbSellUpdateS(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellUpdateS(map);
        return json;
    }

    @PostMapping("/BbSellaSelect")
    public JSONObject BbSellaSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellaSelect(map);
        return json;
    }


    @PostMapping("/BbSellbSelect")
    public JSONObject BbSellbSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellbSelect(map);
        return json;
    }

    @PostMapping("/BbSellAppSelect")
    public JSONObject BbSellAppSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellAppSelect(map);
        return json;
    }

    @PostMapping("/BbSellAppSellSelect")
    public JSONObject BbSellAppSellSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbSellInterface.BbSellAppSellSelect(map);
        return json;
    }


}
