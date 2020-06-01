package com.yt.houserent.houserentconsumer80.houseRentController;

import com.alibaba.fastjson.JSON;
import com.api.entities.Orders;
import com.api.houseRentService.OrderServiceClient;
import com.api.utils.BackEndResp;
import com.yt.houserent.houserentconsumer80.webSocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private WebSocketServer webSocketServer;

    protected static final Logger log= LoggerFactory.getLogger(OrderController.class);
    @RequestMapping(value = "/orders/add",method = RequestMethod.POST)
    public BackEndResp add(@RequestBody Orders order){
        log.info("用户开始添加预定单");
        BackEndResp backEndResp=BackEndResp.build();
        try{
            log.info("step1:查询用户是否已经预定过该房源");
            List<Orders> orderList=this.orderServiceClient.getOrderListByRentId(order.getRentId());
            for (Orders orders:orderList){
                if (orders.getHouseId()==order.getHouseId()){
                    log.info("该用户已预定过该房源");
                    backEndResp.setRespCode(backEndResp.SUCCESS);
                    backEndResp.setRespMsg("您已预定过该房源");
                    return backEndResp;
                }
            }
            int count=this.orderServiceClient.add(order);
            if (count>0){
                log.info("创建预定单成功");
                Map<String,Object>  map=new HashMap<>();
                map.put("title","预订单消息");
                map.put("message","您收到新的待处理预订单");
                webSocketServer.sendOneMessage(String.valueOf(order.getOwnerId()), JSON.toJSONString(map));
                backEndResp.setRespCode(backEndResp.SUCCESS);
                backEndResp.setRespMsg("预定成功");
            }else {
                log.info("创建预定单失败");
                backEndResp.setRespCode(backEndResp.FAIL);
                backEndResp.setRespMsg("创建预定单失败！");
            }
        }catch (Exception e){
            log.error("创建预定单出错");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("系统创建预定单出错！");
        }
        return backEndResp;
    }

    @RequestMapping(value = "/orders/get/{id}",method = RequestMethod.GET)
    public Orders getOrder(@PathVariable("id") int id){
        return this.orderServiceClient.getOrder(id);
    }

    @RequestMapping(value = "/orders/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByOwnerId(@PathVariable("ownerId") int ownerId){
        log.info("开始查询房主{}所有房子的预定单信息",ownerId);
        List<Orders>  orders=new ArrayList<Orders>();
        try{
           orders= this.orderServiceClient.getOrderListByOwnerId(ownerId);
          log.info("一共查询到{}条预订单信息",orders.size());
        }catch (Exception e){
            log.error("查询预定单出错");
        }
        return orders;
    }

    @RequestMapping(value = "/orders/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByRentId(@PathVariable("rentId") int rentId){
        return this.orderServiceClient.getOrderListByRentId(rentId);
    }

    @RequestMapping(value = "/orders/update",method = RequestMethod.POST)
    public boolean updateOrder(@RequestBody Orders order){
        boolean flag=false;
        try{
            int count=this.orderServiceClient.updateOrder(order) ;
            if (count>0){
                flag=true;
                log.info("更新状态成功");
            }else {
                log.info("更新状态失败");
            }
        }catch (Exception e){
            log.info("更新状态出错");
        }
        return flag;
    }

    @RequestMapping(value = "/orders/delete/{id}",method = RequestMethod.GET)
    public BackEndResp deleteOrderById(@PathVariable("id") int id){
        log.info("用户取消预订");
        BackEndResp backEndResp=BackEndResp.build();
        int count=this.orderServiceClient.deleteOrderById(id);
        if (count>0){
            log.info("删除预订单成功");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("成功取消");
        }else {
            log.info("删除预订单失败");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("取消预订单失败");
        }
        return backEndResp;
    }

    @RequestMapping(value = "/orders/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteOrderByHouseId(@PathVariable("houseId") int houseId){
        return this.orderServiceClient.deleteOrderByHouseId(houseId);
    }
}
