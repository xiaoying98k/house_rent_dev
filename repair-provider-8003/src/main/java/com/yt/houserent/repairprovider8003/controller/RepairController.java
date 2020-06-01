package com.yt.houserent.repairprovider8003.controller;


import com.api.entities.Repair;
import com.yt.houserent.repairprovider8003.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepairController {
    @Autowired
    private RepairService repairService;

    @RequestMapping(value = "/repair/add",method = RequestMethod.POST)
    public int add(@RequestBody Repair repair){
        repairService.addRepair(repair);
        int key=repair.getId();
        System.out.println(key);
        return key;
    }

    @RequestMapping(value = "/repair/get/{id}",method = RequestMethod.GET)
    public Repair getRepair(@PathVariable("id") int id){
        return repairService.findRepairById(id);
    }

    @RequestMapping(value = "/repair/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByOwnerId(@PathVariable("ownerId") int ownerId){
        return repairService.searchListByOwnerId(ownerId);
    }

    @RequestMapping(value = "/repair/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByRentId(@PathVariable("rentId") int rentId){
        return repairService.searchListByRentId(rentId);
    }

    @RequestMapping(value = "/repair/update",method = RequestMethod.POST)
    public int updateRepair(@RequestBody Repair repair){
        return repairService.updateByVO(repair);
    }

    @RequestMapping(value = "/repair/delete/{id}",method = RequestMethod.GET)
    public int deleteRepairById(@PathVariable("id") int id){
        return repairService.deleteRepairById(id);
    }

    @RequestMapping(value = "/repair/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRepairByHouseId(@PathVariable("houseId") int houseId){
        return repairService.deleteRepairByHouseId(houseId);
    }
}
