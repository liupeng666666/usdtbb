package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbUsdtInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/BbUsdt")
public class BbUsdtController {
    @Autowired
    private BbUsdtInterface bbUsdtInterface;

    @PostMapping("BbUsdtInsert")
    public JSONObject LFbAssetInsert(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", pid);
        JSONObject json = bbUsdtInterface.BbUsdtInsert(map);
        return json;
    }

    @PostMapping("BbUsdtSelectOfTixian")
    public JSONObject LFbRechargeSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbUsdtInterface.BbUsdtSelectOfTixianBypage(pid, map);
        return json;
    }

    @PostMapping("BbUsdtLog")
    public JSONObject BbUsdtLog(int page, int num, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbUsdtInterface.SubUsdtLog(pid, page, num);
        return json;
    }
}
