package com.api.houseRentService;

import com.api.entities.Rent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "rent-provider")
public interface RentServiceClient {
    @RequestMapping(value = "/rent/add",method = RequestMethod.POST)
    public int add(@RequestBody Rent rent);

    @RequestMapping(value = "/rent/get/{id}",method = RequestMethod.GET)
    public Rent getRent(@PathVariable("id") int id);

    @RequestMapping(value = "/rent/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Rent> getRentListByOwnerId(@PathVariable("ownerId") int ownerId);

    @RequestMapping(value = "/rent/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Rent> getRentListByRentId(@PathVariable("rentId") int rentId);

    @RequestMapping(value = "/rent/update",method = RequestMethod.POST)
    public int updateRent(@RequestBody Rent rent);

    @RequestMapping(value = "/rent/delete/{id}",method = RequestMethod.GET)
    public int deleteRentById(@PathVariable("id") int id);

    @RequestMapping(value = "/rent/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRentByHouseId(@PathVariable("houseId") int houseId);
}
