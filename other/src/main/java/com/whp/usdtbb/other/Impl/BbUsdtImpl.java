package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.*;
import com.whp.usdtbb.other.Interface.BbUsdtInterface;
import com.whp.usdtbb.utils.MD5Util;
import com.whp.usdtbb.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BbUsdtImpl implements BbUsdtInterface {
    @Autowired
    private BbUsdtDao bbUsdtDao;
    @Autowired
    private LSubUserDao lsubUserDao;
    @Autowired
    private BbSysWalletDao bbSysWalletDao;
    @Autowired
    private BbMoneyDao bbMoneyDao;
    @Autowired
    private BbCurrencyDao bbCurrencyDao;

    @Override
    public JSONObject BbUsdtInsert(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            String code = RedisUtils.get("sms." + map.get("userid").toString(), 7);

            // if (code != null && code != "" && code.equals(map.get("yzm"))) {//验证码正确
            Map<String, Object> bcmap = bbCurrencyDao.BbCurrencyById(map.get("currencyid").toString());
            JSONObject bcjson = new JSONObject(bcmap);
            if (bcjson.getInteger("close") == 1) {
                json.put("bcmap", bcmap);
                json.put("code", 171);
            } else {
                String password = MD5Util.MD5(map.get("password").toString());
                map.put("password", password);
                int num = lsubUserDao.SubUserCount(map);

                if (num > 0) {
                    map.put("from_wallet", map.get("to_address"));

                    Map<String, Object> smap = bbMoneyDao.BbMoneySelectHua(map.get("currencyid").toString(), map.get("userid").toString(), null);
                    if (new BigDecimal(map.get("money").toString()).add(new BigDecimal(map.get("trade").toString())).compareTo(new BigDecimal(smap.get("surplus").toString())) != 1) {
                        JSONObject cjson = new JSONObject();
                        cjson.put("currencyid", map.get("currencyid"));
                        cjson.put("userid", map.get("userid"));
                        cjson.put("surplus", new BigDecimal(map.get("money").toString()).add(new BigDecimal(map.get("trade").toString())));
                        cjson.put("frozen", new BigDecimal(map.get("money").toString()));

                        bbMoneyDao.BbMoneyUpdateHua(cjson);
                            /*System.out.println(cjson.get("state").toString()+"====");
                            System.out.println(cjson.get("type").toString());*/

                        bbUsdtDao.BbUsdtInsert(map);

                        json.put("code", 100);
                    } else {
                        json.put("code", 107);
                    }

                } else {
                    json.put("code", 106);
                }
            }
          /*  } else {//验证码不正确
                json.put("code", 102);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject BbUsdtSelectOfTixianBypage(String userid, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbUsdtDao.BbUsdtSelectOfTixian(userid, map,
                    Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("num").toString()));

            int count = bbUsdtDao.BbUsdtSelectOfTixianCount(userid, map);

            json.put("drawList", list);
            json.put("count", count);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

    @Override
    public JSONObject SubUsdtLog(String userid, int page, int num) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = bbUsdtDao.SubUsdtLog(userid, page, num);
            json.put("usdt", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

}

