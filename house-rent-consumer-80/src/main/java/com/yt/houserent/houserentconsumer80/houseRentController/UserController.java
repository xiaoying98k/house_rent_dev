package com.yt.houserent.houserentconsumer80.houseRentController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.entities.House;
import com.api.entities.Rent;
import com.api.entities.User;
import com.api.houseRentService.HouseServiceClient;
import com.api.houseRentService.RentServiceClient;
import com.api.houseRentService.UserServiceClient;
import com.api.utils.BackEndResp;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.support.DTXUserControls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private RentServiceClient rentServiceClient;
    @Autowired
    private HouseServiceClient houseServiceClient;
    @Autowired
    private RentController rentController;

    @Autowired
    private HttpServletRequest request;
    protected static final Logger log= LoggerFactory.getLogger(UserController.class);
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public int add(@RequestBody User user){
        return this.userServiceClient.add(user);
    }

    @RequestMapping(value = "/user/get/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id){
        log.info("获取用户id为{}的信息",id);
        return userServiceClient.getUser(id);
    }

    //用户登录接口  入参：User(name password)
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public User login(@RequestBody User user){
        log.info("用户开始登陆");
        User loginUser=new User();
        List<User> userList=this.userServiceClient.getUserList(user);
        if (userList.size()>0){
           loginUser=userList.get(0);
            log.info("查询到用户："+loginUser.toString());
            HttpSession session=request.getSession();
            session.setAttribute("user",loginUser);
        }else {
            log.info("用户登录失败！");
        }


        return loginUser;
    }


    //用户注册接口  入参：User
    @RequestMapping(value = "/user/registry",method = RequestMethod.POST)
    public Boolean userRegistry(@RequestBody User user){
        log.info("用户:{}开始注册",user.getName());
        Boolean flag=true;
        try {
            int count=this.userServiceClient.add(user);
            if (count>0){
                log.info(user.getName()+"注册成功！");
            }else {
                flag=false;
                log.info(user.getName()+"注册失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }



        return flag;
    }


    //修改用户资料
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public Boolean updateUser(@RequestBody User user){
        Boolean flag=false;
        log.info(user.getName()+"开始修改资料");
        try{
            int count=this.userServiceClient.updateUser(user);
            if (count>0){
                log.info(user.getName()+"修改资料成功");
                flag=true;
            }else {
                log.info(user.getName()+"修改资料失败");
            }
        }catch (Exception e){
            log.info("修改资料出错");
            e.printStackTrace();
        }


        return flag;
    }

    @RequestMapping(value = "/user/delete/{id}",method = RequestMethod.GET)
    public int deleteUser(@PathVariable("id") int id){
        return this.userServiceClient.deleteUser(id);
    }

    //用户支付(首租)
    @LcnTransaction
    @RequestMapping(value = "/user/payment",method = RequestMethod.POST)
    public BackEndResp pay(@RequestBody String payJson){
        BackEndResp backEndResp=BackEndResp.build();
        log.info("用户支付开始");
        log.info(payJson);
        BackEndResp payBackEndResp=this.userServiceClient.pay(payJson);
        log.info(payBackEndResp.getRespCode());
        log.info(payBackEndResp.getRespMsg());
        if (payBackEndResp.getRespCode().equals(payBackEndResp.FAIL)){
            log.info("支付过程失败");
            return payBackEndResp;
        }
        log.info("支付中开启事务");
        log.info("开始创建订单");
        Rent rent=new Rent();
        JSONObject rentObj=JSONObject.parseObject(payJson);
        rent.setRental(rentObj.getBigDecimal("rental"));
        rent.setDeposit(rentObj.getBigDecimal("deposit"));
        rent.setTenancy(rentObj.getString("tenancy"));
        rent.setHouseId(rentObj.getIntValue("houseId"));
        rent.setRentId(rentObj.getIntValue("payerId"));
        rent.setOwnerId(rentObj.getIntValue("payeeId"));
        rent.setState("首租");
        log.info(rent.toString());
        BackEndResp rentBackEndResp=rentController.add(rent);
        if (payBackEndResp.getRespCode().equals(payBackEndResp.SUCCESS)&&rentBackEndResp.getRespCode().equals(rentBackEndResp.SUCCESS)){
            log.info("支付和创建订单流程成功完成");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("支付成功,已为您成功创建租住订单，请查看");
        }else {
            log.info("支付或创建订单流程出错");
            log.info("创建订单流程出错,事务开始回滚");
            DTXUserControls.rollbackCurrentGroup();
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("很抱歉，支付失败:"+rentBackEndResp.getRespMsg());
        }

        return backEndResp;

    }


    //用户续租
    @LcnTransaction
    @RequestMapping(value = "/user/reRent",method = RequestMethod.POST)
    public BackEndResp reRent(@RequestBody String reRentJson){
        BackEndResp backEndResp=BackEndResp.build();
        log.info("用户续租，支付开始");
        log.info(reRentJson);
        BackEndResp payBackEndResp=this.userServiceClient.pay(reRentJson);
        log.info(payBackEndResp.getRespCode());
        log.info(payBackEndResp.getRespMsg());
        if (payBackEndResp.getRespCode().equals(payBackEndResp.FAIL)){
            log.info("支付过程失败");
            return payBackEndResp;
        }
        log.info("支付中开启事务");
        log.info("开始更新订单");
        Rent rent=new Rent();
        JSONObject reRentObj=JSONObject.parseObject(reRentJson);
        rent.setId(reRentObj.getIntValue("id"));
        rent.setRental(reRentObj.getBigDecimal("money"));
        rent.setTenancy(reRentObj.getString("tenancy"));
        rent.setHouseId(reRentObj.getIntValue("houseId"));
        rent.setRentId(reRentObj.getIntValue("payerId"));
        rent.setOwnerId(reRentObj.getIntValue("payeeId"));
        rent.setState("续租中");
        rent.setUrge("N");//取消催租提醒
        log.info(rent.toString());
        int count=this.rentServiceClient.updateRent(rent);
        System.out.println(count);
        if (count>0){
            log.info("更新订单成功，续租完成");
        }
        if (payBackEndResp.getRespCode().equals(payBackEndResp.SUCCESS)&&(count>0)){
            log.info("支付和更新订单(续租)完成");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("成功续租！");
        }else {
            log.info("续租流程出错,事务开始回滚");
            DTXUserControls.rollbackCurrentGroup();
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("很抱歉，操作失败");
        }

        return backEndResp;

    }

    //用户退租
    @LcnTransaction
    @RequestMapping(value = "/user/giveBackRent",method = RequestMethod.POST)
    public BackEndResp giveBackRent(@RequestBody String giveBackJson){
        BackEndResp backEndResp=BackEndResp.build();
        log.info("用户退租，结算支付开始");
        log.info(giveBackJson);
        //调用支付模块
        BackEndResp payBackEndResp=this.userServiceClient.pay(giveBackJson);
        log.info(payBackEndResp.getRespCode());
        log.info(payBackEndResp.getRespMsg());
        if (payBackEndResp.getRespCode().equals(payBackEndResp.FAIL)){
            log.info("支付过程失败");
            return payBackEndResp;
        }
        log.info("支付中开启事务");
        log.info("开始更新订单状态----》退租");
        Rent rent=new Rent();
        JSONObject giveBackObj=JSONObject.parseObject(giveBackJson);
        rent.setId(giveBackObj.getIntValue("id"));
        rent.setHouseId(giveBackObj.getIntValue("houseId"));
        rent.setRentId(giveBackObj.getIntValue("payerId"));
        rent.setOwnerId(giveBackObj.getIntValue("payeeId"));
        rent.setUrge("N");//取消催租提醒
        rent.setState("已退租,待退押金");
        log.info(rent.toString());
        int count=this.rentServiceClient.updateRent(rent);
        //更新所租房源状态
        House house=new House();
        house.setId(giveBackObj.getIntValue("houseId"));
        house.setRenting(1);//释放资源
        int count2=this.houseServiceClient.updateHouse(house);
        System.out.println(count);
        if (count>0&&count2>0){
            log.info("更新订单成功，释放房源成功，退租完成");
        }
        if (payBackEndResp.getRespCode().equals(payBackEndResp.SUCCESS)&&(count>0)&&(count2>0)){
            log.info("支付和更新订单(退租)完成");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("成功退租！");
        }else {
            log.info("退租流程出错,事务开始回滚");
            DTXUserControls.rollbackCurrentGroup();
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("很抱歉，操作失败");
        }

        return backEndResp;

    }

    //房主退还押金
    @LcnTransaction
    @RequestMapping(value = "/user/giveBackDeposit",method = RequestMethod.POST)
    public BackEndResp giveBackDeposit(@RequestBody String depositJson){
        BackEndResp backEndResp=BackEndResp.build();
        log.info("房主退还押金开始");
        log.info(depositJson);
        //调用支付模块
        BackEndResp payBackEndResp=this.userServiceClient.pay(depositJson);
        log.info(payBackEndResp.getRespCode());
        log.info(payBackEndResp.getRespMsg());
        if (payBackEndResp.getRespCode().equals(payBackEndResp.FAIL)){
            log.info("支付过程失败");
            return payBackEndResp;
        }
        log.info("支付中开启事务");
        log.info("开始更新订单状态----》退租,已退押金");
        Rent rent=new Rent();
        JSONObject giveBackObj=JSONObject.parseObject(depositJson);
        rent.setId(giveBackObj.getIntValue("id"));
        rent.setHouseId(giveBackObj.getIntValue("houseId"));
        rent.setRentId(giveBackObj.getIntValue("payeeId"));
        rent.setOwnerId(giveBackObj.getIntValue("payerId"));
        rent.setUrge("N");//取消催租提醒
        rent.setState("已退租,已退押金");
        log.info(rent.toString());
        int count=this.rentServiceClient.updateRent(rent);

        if (count>0){
            log.info("更新订单成功，退还押金完成");
        }
        if (payBackEndResp.getRespCode().equals(payBackEndResp.SUCCESS)&&(count>0)){
            log.info("支付和更新订单(退还押金)完成");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("成功退还押金！");
        }else {
            log.info("退还押金流程出错,事务开始回滚");
            DTXUserControls.rollbackCurrentGroup();
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("很抱歉，操作失败");
        }

        return backEndResp;

    }

}
