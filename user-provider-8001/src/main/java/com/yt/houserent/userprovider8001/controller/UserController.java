package com.yt.houserent.userprovider8001.controller;

import com.alibaba.fastjson.JSONObject;
import com.api.entities.User;
import com.api.utils.BackEndResp;
import com.yt.houserent.userprovider8001.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    protected static final Logger log= LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public int add(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/user/get/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/user/doSearch",method = RequestMethod.POST)
    public List<User> getUserList(@RequestBody User user){
        return userService.searchListByVO(user);
    }

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public int updateUser(@RequestBody User user){
        return userService.updateByVO(user);
    }


    //支付模块
    @RequestMapping(value = "/user/pay",method = RequestMethod.POST)
    public BackEndResp pay(@RequestBody String payJson){
        BackEndResp backEndResp=BackEndResp.build();
        JSONObject payMap=JSONObject.parseObject(payJson);
        int payerId=payMap.getIntValue("payerId");
        int payeeId=payMap.getIntValue("payeeId");
        String payPwd=payMap.getString("payPwd");
        BigDecimal money=payMap.getBigDecimal("money");
        User user=userService.findUserById(payerId);
        if (!user.getPayPwd().equals(payPwd)){
            log.info("支付密码错误");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("支付密码错误");
            return backEndResp;
        }
        backEndResp=userService.pay(payeeId,payerId,money);
        return backEndResp;
    }

    @RequestMapping(value = "/user/delete/{id}",method = RequestMethod.GET)
    public int deleteUser(@PathVariable("id") int id){
        return userService.deleteUserById(id);
    }





}

