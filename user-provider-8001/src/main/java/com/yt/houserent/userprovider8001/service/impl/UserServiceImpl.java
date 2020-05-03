package com.yt.houserent.userprovider8001.service.impl;

import com.api.entities.User;
import com.api.utils.BackEndResp;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.yt.houserent.userprovider8001.dao.UserDao;
import com.yt.houserent.userprovider8001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int deleteUserById(int id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> searchListByVO(User user) {
        return userDao.searchListByVO(user);
    }

    @Override
    public int updateByVO(User user) {
        return userDao.updateByVO(user);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public BackEndResp pay(int payeeId, int payerId, BigDecimal money) {
        BackEndResp backEndResp=BackEndResp.build();
        System.out.println("step1：查找支付方金额信息");
        User rent=userDao.findUserById(payerId);
        if (rent.getMoney().compareTo(money)==-1){
            System.out.println("支付方余额"+rent.getMoney()+"不足");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("您的余额不足！");
            return backEndResp;
        }
        int count=userDao.updateForPay(payeeId,payerId,money);
        System.out.println("更新的结果数为"+count);
        if (count==1){
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("支付成功！");
        }else {
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("支付失败！");
        }
        return backEndResp;
    }
}
