package com.yt.houserent.rentprovider8004.controller;


import com.api.entities.Rent;
import com.yt.houserent.rentprovider8004.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class RentController {
    @Autowired
    private RentService rentService;
    @RequestMapping(value = "/rent/add",method = RequestMethod.POST)
    public int add(@RequestBody Rent rent){
        return rentService.addRent(rent);
    }

    @RequestMapping(value = "/rent/get/{id}",method = RequestMethod.GET)
    public Rent getRent(@PathVariable("id") int id){
        return rentService.findRentById(id);
    }

    @RequestMapping(value = "/rent/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Rent> getRentListByOwnerId(@PathVariable("ownerId") int ownerId){
        return rentService.searchListByOwnerId(ownerId);
    }

    @RequestMapping(value = "/rent/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Rent> getRentListByRentId(@PathVariable("rentId") int rentId){
        return rentService.searchListByRentId(rentId);
    }

    @RequestMapping(value = "/rent/update",method = RequestMethod.POST)
    public int updateRent(@RequestBody Rent rent){
        return rentService.updateByVO(rent);
    }

    @RequestMapping(value = "/rent/delete/{id}",method = RequestMethod.GET)
    public int deleteRentById(@PathVariable("id") int id){
        return rentService.deleteRentById(id);
    }

    @RequestMapping(value = "/rent/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRentByHouseId(@PathVariable("houseId") int houseId){
        return rentService.deleteRentByHouseId(houseId);
    }
}
