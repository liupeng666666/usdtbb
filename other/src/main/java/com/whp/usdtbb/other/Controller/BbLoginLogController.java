package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbLoginLogInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;

/**
 * @author : 张吉伟
 * @data : 2019/7/27 10:57
 * @descrpition :
 */
@RestController
@RequestMapping("BbLoginLog")
public class BbLoginLogController {
    @Autowired
    private BbLoginLogInterface bbLoginLogInterface;

    @PostMapping("BbLoginLogSelect")
    public JSONObject BbLoginLogSelect(int page, int num, HttpServletRequest request) {

        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbLoginLogInterface.BbLoginLogSelect(userid, page, num);
        return json;
    }
}
