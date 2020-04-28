package com.whp.usdtbb.other.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Interface.BbSellInterface;
import com.whp.usdtbb.other.Interface.BbTransferInterface;
import com.whp.usdtbb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/BbTransfer")
public class BbTransferController {
    @Autowired
    private BbTransferInterface bbTransferInterface;

    @PostMapping("BbTransfer")
    public JSONObject BbTransfer(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbTransferInterface.FbTransferInsert(map, pid);
        return json;
    }

    @PostMapping("BbTransferDetailSelect")
    public JSONObject BbTransferDetailSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = bbTransferInterface.BbTransferDetailSelect(map, userid);
        return json;
    }

}
