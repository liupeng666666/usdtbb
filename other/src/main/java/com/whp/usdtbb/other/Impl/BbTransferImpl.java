package com.whp.usdtbb.other.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtbb.other.Dao.BbCurrencyDao;
import com.whp.usdtbb.other.Dao.BbMoneyDao;
import com.whp.usdtbb.other.Dao.BbTransferDao;
import com.whp.usdtbb.other.Dao.LBbAssetOrederDao;
import com.whp.usdtbb.other.Interface.BbTransferInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BbTransferImpl implements BbTransferInterface {
    @Autowired
    private BbCurrencyDao bbCurrencyDao;
    @Autowired
    private BbMoneyDao bbMoneyDao;
    @Autowired
    private LBbAssetOrederDao lBbAssetOrederDao;
    @Autowired
    private BbTransferDao bbTransferDao;

    @Override
    public JSONObject BbTransferDetailSelect(Map<String, Object> map, String userid) {
        JSONObject json = new JSONObject();
        try {

            List<Map<String, Object>> list = bbTransferDao.BbTransferDetailSelect(map, userid);
            if (list.size() > 0) {
                json.put("detailList", list);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }


        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject FbTransferInsert(Map<String, Object> map, String userid) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject(map);
            Map<String, Object> fmap = bbCurrencyDao.FbCurrencySelectByName(jsonObject.getString("name"));//传过来的名称查法币
            Map<String, Object> bmap = bbCurrencyDao.BbCurrencyHua(jsonObject.getString("name"));//传过来的名称查币币
            if ("3".equals(jsonObject.get("zhanghu"))) {
                if ("2".equals(jsonObject.get("toAccount"))) {//选择转到现货账户
                    if ("BRU".equals(jsonObject.get("name")) || "USDT".equals(jsonObject.get("name"))) {
                        if (bmap == null) {
                            json.put("code", 104);
                        } else {//存在
                            if (jsonObject.getInteger("state") == 3) {
                                Map<String, Object> zmap = bbMoneyDao.BbMoneySelectHua(bmap.get("pid").toString(), userid, null);
                                if (zmap == null) {
                                    json.put("code", 104);
                                } else {
                                    JSONObject jsonObject1 = new JSONObject(zmap);
                                    if (jsonObject1.getBigDecimal("surplus").compareTo(jsonObject.getBigDecimal("money")) == -1) {
                                        json.put("code", 105);
                                    } else {//划转数量正确
                                        JSONObject cjson = new JSONObject();
                                        cjson.put("currencyid", bmap.get("pid").toString());
                                        cjson.put("userid", userid);
                                        cjson.put("surplus", jsonObject.getBigDecimal("money"));
                                        cjson.put("type", "4");//bibi转现货
                                        bbMoneyDao.BbMoneyUpdateHua(cjson);

                                        JSONObject cjson2 = new JSONObject();//修改现货表
                                        cjson2.put("currencyToName", jsonObject.getString("name"));
                                        cjson2.put("surplusAdd", jsonObject.getBigDecimal("money"));
                                        cjson2.put("userid", userid);

                                        lBbAssetOrederDao.LSubAssetAdd(cjson2, userid);

                                        lBbAssetOrederDao.LFbAssetTransferIns(bmap.get("pid").toString(), jsonObject.getString("name"), jsonObject.getBigDecimal("money"), jsonObject.getBigDecimal("money"), userid, "4");

                                        json.put("code", 100);
                                    }
                                }
                            }
                        }
                    } else {
                        json.put("code", 104);
                    }
                } else if (("1").equals(jsonObject.get("toAccount"))) {
                    if (fmap != null) {
                        String currencyid = fmap.get("pid").toString();
                        String bbCurrencyid = bmap.get("pid").toString();
                        if (jsonObject.getInteger("state") == 3) {
                            Map<String, Object> bmmap = bbMoneyDao.BbMoneySelectHua(bbCurrencyid, userid, null);

                            if (bmmap == null) {
                                json.put("code", 104);
                                return json;
                            } else {
                                JSONObject jsonObject1 = new JSONObject(bmmap);
                                if (jsonObject1.getBigDecimal("surplus").compareTo(jsonObject.getBigDecimal("money")) == -1) {
                                    json.put("code", 105);
                                } else {
                                    JSONObject cjson = new JSONObject();
                                    cjson.put("currencyid", bbCurrencyid);
                                    cjson.put("userid", userid);
                                    cjson.put("surplus", jsonObject.getBigDecimal("money"));
                                    cjson.put("type", "5");//bibi转法币
                                    bbMoneyDao.BbMoneyUpdateHua(cjson);

                                    JSONObject cjson1 = new JSONObject();//修改法币表
                                    cjson1.put("surplusAdd", jsonObject.getBigDecimal("money"));
                                    cjson1.put("userid", userid);
                                    cjson1.put("currencyid", currencyid);
                                    cjson1.put("surplus", jsonObject.getBigDecimal("money"));
                                    cjson1.put("type", "5");
                                    cjson1.put("name", jsonObject.getString("name"));
                                    Map<String, Object> bfmap = bbMoneyDao.FbMoneySelectForBb(currencyid, userid, null);

                                    if (bfmap == null) {
                                        lBbAssetOrederDao.LFbAssetInsert(cjson1, userid);

                                    } else {
                                        lBbAssetOrederDao.LFbAssetAdd(cjson1, userid);

                                    }

                                    lBbAssetOrederDao.LFbAssetTransferIns(bmap.get("pid").toString(), fmap.get("pid").toString(), jsonObject.getBigDecimal("money"), jsonObject.getBigDecimal("money"), userid, "5");

                                    json.put("code", 100);

                                }
                            }
                        }
                    } else {
                        json.put("code", 104);
                    }
                }
            } else {
                if (jsonObject.getInteger("state") == 1) {
                    if (bmap != null) {//币币不为空可转
                        String currencyid = fmap.get("pid").toString();//得到法币id
                        String bbCurrencyid = bmap.get("pid").toString();//得到BI币id
                        Map<String, Object> fmmap = bbMoneyDao.FbMoneySelectForBb(currencyid, userid, null);

                        if (fmmap == null) {
                            json.put("code", 104);
                        } else {
                            JSONObject jsonObject1 = new JSONObject(fmmap);
                            if (jsonObject1.getBigDecimal("surplus").compareTo(jsonObject.getBigDecimal("money")) == -1) {
                                json.put("code", 105);
                            } else {
                                JSONObject cjson = new JSONObject();
                                cjson.put("currencyid", currencyid);
                                cjson.put("userid", userid);
                                cjson.put("surplusSub", jsonObject.getBigDecimal("money"));
                                cjson.put("type", "6");//法币转bibi
                                //更新法币
                                lBbAssetOrederDao.LFbAssetSub(cjson, userid);


                                JSONObject cjson1 = new JSONObject();//修改币币表
                                cjson1.put("surplusAdd", jsonObject.getBigDecimal("money"));
                                cjson1.put("userid", userid);
                                cjson1.put("currencyid", bbCurrencyid);
                                cjson1.put("type", "6");
                                cjson1.put("name", jsonObject.getString("name"));
                                Map<String, Object> bmmap = bbMoneyDao.BbMoneySelectHua(bbCurrencyid, userid, null);
                                if (bmmap == null) {//更新或插入操作的币币钱数
                                    bbMoneyDao.BbMoneyInsertHua(cjson1, userid);

                                } else {
                                    bbMoneyDao.BbMoneyUpdateHuaAdd(cjson1);

                                }
                                lBbAssetOrederDao.LFbAssetTransferIns(fmap.get("pid").toString(), bmap.get("pid").toString(), jsonObject.getBigDecimal("money"), jsonObject.getBigDecimal("money"), userid, "6");

                                json.put("code", 100);
                            }
                        }
                    } else {
                        json.put("code", 104);
                    }
                }
                if (jsonObject.getInteger("state") == 2) {
                    if (bmap != null) {
                        String bbCurrencyid = bmap.get("pid").toString();//得到BI币id
                        Map<String, Object> xbmap = lBbAssetOrederDao.LXhAssetSelect(userid);
                        if (xbmap == null) {
                            json.put("code", 104);
                        } else {
                            JSONObject jsonObject1 = new JSONObject(xbmap);

                            if ("USDT".equals(jsonObject.get("name"))) {
                                if (jsonObject1.getBigDecimal("surplus").compareTo(jsonObject.getBigDecimal("money")) == -1) {
                                    json.put("code", 105);
                                    return json;
                                }
                            } else {
                                if (jsonObject1.getBigDecimal("bur_money").compareTo(jsonObject.getBigDecimal("money")) == -1) {
                                    json.put("code", 105);
                                    return json;
                                }
                            }

                            JSONObject cjson = new JSONObject();
                            cjson.put("currencyFrmName", jsonObject.getString("name"));
                            cjson.put("userid", userid);
                            cjson.put("surplusSub", jsonObject.getBigDecimal("money"));
                            cjson.put("type", "7");//现货转bibi
                            lBbAssetOrederDao.LSubAssetSub(cjson, userid);

                            JSONObject cjson1 = new JSONObject();//修改币币表
                            cjson1.put("surplusAdd", jsonObject.getBigDecimal("money"));
                            cjson1.put("userid", userid);
                            cjson1.put("currencyid", bbCurrencyid);
                            cjson1.put("type", "7");
                            cjson1.put("name", jsonObject.getString("name"));

                            Map<String, Object> bmmap = bbMoneyDao.BbMoneySelectHua(bbCurrencyid, userid, null);
                            if (bmmap == null) {//更新或插入操作的币币钱数
                                bbMoneyDao.BbMoneyInsertHua(cjson1, userid);
                            } else {
                                bbMoneyDao.BbMoneyUpdateHuaAdd(cjson1);
                            }

                            lBbAssetOrederDao.LFbAssetTransferIns(jsonObject.getString("name"), bmap.get("pid").toString(), jsonObject.getBigDecimal("money"), jsonObject.getBigDecimal("money"), userid, "7");

                            json.put("code", 100);


                        }
                    } else {
                        json.put("code", 104);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }


}
