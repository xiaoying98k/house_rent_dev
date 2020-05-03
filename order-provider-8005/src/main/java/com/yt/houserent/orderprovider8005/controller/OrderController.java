package com.yt.houserent.orderprovider8005.controller;


import com.api.entities.Orders;
import com.yt.houserent.orderprovider8005.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping(value = "/orders/add",method = RequestMethod.POST)
    public int add(@RequestBody Orders order){
        return orderService.addOrder(order);
    }

    @RequestMapping(value = "/orders/get/{id}",method = RequestMethod.GET)
    public Orders getOrder(@PathVariable("id") int id){
        return orderService.findOrderById(id);
    }

    @RequestMapping(value = "/orders/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByOwnerId(@PathVariable("ownerId") int ownerId){
        return orderService.searchListByOwnerId(ownerId);
    }

    @RequestMapping(value = "/orders/doSearchByRentId{rentId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByRentId(@PathVariable("rentId") int rentId){
        return orderService.searchListByRentId(rentId);
    }

    @RequestMapping(value = "/orders/update",method = RequestMethod.POST)
    public int updateOrder(@RequestBody Orders order){
        return orderService.updateByVO(order);
    }

    @RequestMapping(value = "/orders/delete/{id}",method = RequestMethod.GET)
    public int deleteOrderById(@PathVariable("id") int id){
        return orderService.deleteOrderById(id);
    }

    @RequestMapping(value = "/orders/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteOrderByHouseId(@PathVariable("houseId") int houseId){
        return orderService.deleteOrderByHouseId(houseId);
    }
}
