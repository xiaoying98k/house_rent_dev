package com.yt.houserent.houserentconsumer80.houseRentController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.entities.House;
import com.api.entities.Rent;
import com.api.entities.Repair;
import com.api.entities.User;
import com.api.houseRentService.HouseServiceClient;
import com.api.houseRentService.RentServiceClient;
import com.api.houseRentService.RepairServiceClient;
import com.api.houseRentService.UserServiceClient;
import com.api.utils.BackEndResp;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.yt.houserent.houserentconsumer80.webSocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RentController {
    @Autowired
    private RentServiceClient rentServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RepairServiceClient repairServiceClient;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private HouseServiceClient houseServiceClient;

    protected static final Logger log= LoggerFactory.getLogger(RentController.class);

    @LcnTransaction
    @RequestMapping(value = "/rent/add",method = RequestMethod.POST)
    public BackEndResp add(@RequestBody Rent rent){
        BackEndResp backEndResp=BackEndResp.build();
        House house=new House();
        House houseInfo=this.houseServiceClient.findHouseById(rent.getHouseId());
        if (houseInfo.getRenting()==2&&houseInfo.getRentType().equals("整租")){//如果已经处于出租状态,并且状态为整租
            log.info("生成租住订单失败,该房屋已被别人抢先一步付款");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("抱歉,生成租住订单失败,该房屋已被别人抢先一步付款");
        }else {
            int count=this.rentServiceClient.add(rent);//添加到订单表
            house.setId(rent.getHouseId());
            house.setRenting(2);//处于出租状态
            int count2=this.houseServiceClient.updateHouse(house);
            if (count>0&&count2>0){
                log.info("成功生成租住订单");
                backEndResp.setRespMsg("成功生成租住订单");
                backEndResp.setRespCode(backEndResp.SUCCESS);
            }else {
                log.info("生成租住订单失败");
                backEndResp.setRespCode(backEndResp.FAIL);
                backEndResp.setRespMsg("生成租住订单失败");
            }
        }

        return backEndResp;
    }

    @RequestMapping(value = "/rent/get/{id}",method = RequestMethod.GET)
    public Rent getRent(@PathVariable("id") int id){
        return this.rentServiceClient.getRent(id);
    }

    @RequestMapping(value = "/rent/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Map> getRentListByOwnerId(@PathVariable("ownerId") int ownerId){
        log.info("开始根据房主id查询所有租房订单信息");
        List<Map> rents=new ArrayList<>();
        List<Rent> rentList=this.rentServiceClient.getRentListByOwnerId(ownerId);
        for (int i=0;i<rentList.size();i++){
            Map infoMap=new HashMap();
            Rent rent=new Rent();
            rent=rentList.get(i);
            log.info("根据订单表循环查找租户和房屋信息houseId="+ rent.getHouseId());
            infoMap.put("rent",rent);
            //获取租户信息
            User tenant=this.userServiceClient.getUser(rent.getRentId());
            infoMap.put("tenant",tenant);
            //获取房屋信息
            House house=this.houseServiceClient.findHouseById(rent.getHouseId());
            infoMap.put("house",house);
            rents.add(infoMap);
        }
        return rents;
    }

    @RequestMapping(value = "/rent/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Map> getRentListByRentId(@PathVariable("rentId") int rentId){
        log.info("开始根据租客id查询所有租房订单信息");
       List<Map> rents=new ArrayList<>();
        List<Rent> rentList=this.rentServiceClient.getRentListByRentId(rentId);
        for (int i=0;i<rentList.size();i++){
            Map infoMap=new HashMap();
            Rent rent=new Rent();
            rent=rentList.get(i);
            log.info("循环查找房东和房屋信息houseId="+ rent.getHouseId());
            infoMap.put("rent",rent);
            //获取房主信息
            User owner=this.userServiceClient.getUser(rent.getOwnerId());
            infoMap.put("owner",owner);
            //获取房屋信息
            House house=this.houseServiceClient.findHouseById(rent.getHouseId());
            infoMap.put("house",house);
            rents.add(infoMap);
        }
        return rents;
    }

    @RequestMapping(value = "/rent/searchRentAndRepairByRentId//{rentId}",method = RequestMethod.GET)
    public List<Map> getRentAndRepairByRentId(@PathVariable("rentId") int rentId){
        log.info("开始根据租客id查询所有租房订单信息");
        List<Map> rents=new ArrayList<>();
        List<Rent> rentList=this.rentServiceClient.getRentListByRentId(rentId);
        //获取维修表的信息
        List<Repair> repairList=repairServiceClient.getRepairListByRentId(rentId);
        for (int i=0;i<rentList.size();i++){
            Map infoMap=new HashMap();
            Rent rent=new Rent();
            rent=rentList.get(i);
            log.info("循环查找房东和房屋信息houseId="+ rent.getHouseId());
            infoMap.put("rent",rent);
            //获取房主信息
            User owner=this.userServiceClient.getUser(rent.getOwnerId());
            infoMap.put("owner",owner);
            //获取房屋信息
            House house=this.houseServiceClient.findHouseById(rent.getHouseId());

            //获取与订单相关的物品维修信息
            infoMap.put("repair",null);
            for (Repair repair:repairList){
                if (repair.getHouseId()==rent.getHouseId()){//租户租住的房子需要维修的物品
                    infoMap.put("repair",repair);
                    break;
                }
            }
            infoMap.put("house",house);
            rents.add(infoMap);
        }
        return rents;
    }
    @RequestMapping(value = "/rent/urge",method = RequestMethod.POST)
    public BackEndResp urge(@RequestBody String urgeJson){
        System.out.println(urgeJson);
        JSONObject urge=JSON.parseObject(urgeJson);
        int rentId=urge.getIntValue("rentId");
        int tenantId=urge.getIntValue("tenantId");
        String message=urge.getString("message");
        String houseNumber=urge.getString("houseNumber");
        Map<String,Object> map=new HashMap<>();
        map.put("title","催租提醒");
        map.put("message",message);
        map.put("houseNumber",houseNumber);
        map.put("urgeTime",new SimpleDateFormat("MM-dd hh:mm").format(new Date()));
        String mapJson=JSONObject.toJSONString(map);
        log.info("房主向租客发起催租提醒");
        BackEndResp backEndResp=BackEndResp.build();
        Rent rent=new Rent();
        rent.setId(rentId);
        rent.setUrge(mapJson);
        int count=this.rentServiceClient.updateRent(rent);
        if (count>0){
            webSocketServer.sendOneMessage(String.valueOf(tenantId),mapJson);
            log.info("催租成功");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("催租成功");
        }else {
            log.info("催租失败");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("催租失败");
        }
        return  backEndResp;
    }

    @RequestMapping(value = "/rent/update",method = RequestMethod.POST)
    public int updateRent(@RequestBody Rent rent){
        return this.rentServiceClient.updateRent(rent);
    }

    @RequestMapping(value = "/rent/delete/{id}",method = RequestMethod.GET)
    public int deleteRentById(@PathVariable("id") int id){
        return this.rentServiceClient.deleteRentById(id);
    }

    @RequestMapping(value = "/rent/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRentByHouseId(@PathVariable("houseId") int houseId){
        return this.rentServiceClient.deleteRentByHouseId(houseId);
    }
}
