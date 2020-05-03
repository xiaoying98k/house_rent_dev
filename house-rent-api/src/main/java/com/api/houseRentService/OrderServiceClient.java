package com.api.houseRentService;

import com.api.entities.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "order-provider")
public interface OrderServiceClient {
    @RequestMapping(value = "/orders/add",method = RequestMethod.POST)
    public int add(@RequestBody Orders order);

    @RequestMapping(value = "/orders/get/{id}",method = RequestMethod.GET)
    public Orders getOrder(@PathVariable("id") int id);

    @RequestMapping(value = "/orders/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByOwnerId(@PathVariable("ownerId") int ownerId);

    @RequestMapping(value = "/orders/doSearchByRentId{rentId}",method = RequestMethod.GET)
    public List<Orders> getOrderListByRentId(@PathVariable("rentId") int rentId);

    @RequestMapping(value = "/orders/update",method = RequestMethod.POST)
    public int updateOrder(@RequestBody Orders order);

    @RequestMapping(value = "/orders/delete/{id}",method = RequestMethod.GET)
    public int deleteOrderById(@PathVariable("id") int id);

    @RequestMapping(value = "/orders/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteOrderByHouseId(@PathVariable("houseId") int houseId);
}
