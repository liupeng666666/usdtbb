package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbFreeInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/BbFree")
public class BbFreeController {
    @Autowired
    private BbFreeInterface bbFreeInterface;

    @PostMapping("BbFreeInsert")
    public JSONObject BbFreeInsert(@RequestParam String id, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbFreeInterface.BbFreeInsert(id, userid);
        return json;
    }

    @PostMapping("BbFreeDel")
    public JSONObject BbFreeDel(@RequestParam String id, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbFreeInterface.BbFreeDel(id, userid);
        return json;
    }

    @PostMapping("BbFreeUpdate")
    public JSONObject BbFreeUpdate(@RequestParam("pids[]") String[] ids, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        for (int i = 0; i < ids.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("sort", i + 1);
            map.put("userid", userid);
            map.put("currencyid", ids[i]);
            json = bbFreeInterface.BbFreeUpdate(map);
        }

        return json;
    }
}
