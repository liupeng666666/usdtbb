package com.whp.usdtbb.user.Impl;

import com.whp.usdtbb.user.Dao.SubUserDao;
import com.whp.usdtbb.user.Interface.SubUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2019/6/14 9:47
 * @descrpition :
 */
@Service
public class SubUserImpl implements SubUserInterface {
    @Autowired
    private SubUserDao subUserDao;

    @Override
    public Map<String, Object> getSubUserByUser(String pid) {
        return subUserDao.getSubUserByUser(pid);
    }
}
