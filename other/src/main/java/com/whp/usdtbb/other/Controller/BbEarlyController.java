package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbEarlyInterface;
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
 * @data : 2019/7/18 9:26
 * @descrpition :
 */
@RestController
@RequestMapping("BbEarly")
public class BbEarlyController {
    @Autowired
    private BbEarlyInterface bbEarlyInterface;

    @PostMapping("BbEarlyRecordInsert")
    public JSONObject BbEarlyRecordInsert(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        JSONObject json = bbEarlyInterface.BbEarlyRecordInsert(map);
        return json;
    }

    @PostMapping("BbEarlyRecordSelect")
    public JSONObject BbEarlyRecordSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        JSONObject json = bbEarlyInterface.BbEarlyRecordSelect(map);
        return json;
    }

    @PostMapping("BbEarlyRecordUpdate")
    public JSONObject BbEarlyRecordUpdate(@RequestParam Map<String, Object> map) {
        JSONObject json = bbEarlyInterface.BbEarlyRecordUpdate(map);
        return json;
    }

    @PostMapping("BbEarlyRecordLogSelect")
    public JSONObject BbEarlyRecordLogSelect(int num, int page, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbEarlyInterface.BbEarlyRecordLogSelect(userid, page, num);
        return json;
    }
}
