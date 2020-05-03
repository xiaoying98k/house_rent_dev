package com.api.houseRentService;

import com.api.entities.Repair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "repair-provider")
public interface RepairServiceClient {
    @RequestMapping(value = "/repair/add",method = RequestMethod.POST)
    public int add(@RequestBody Repair repair);

    @RequestMapping(value = "/repair/get/{id}",method = RequestMethod.GET)
    public Repair getRepair(@PathVariable("id") int id);

    @RequestMapping(value = "/repair/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByOwnerId(@PathVariable("ownerId") int ownerId);

    @RequestMapping(value = "/repair/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByRentId(@PathVariable("rentId") int rentId);


    @RequestMapping(value = "/repair/update",method = RequestMethod.POST)
    public int updateRepair(@RequestBody Repair repair);

    @RequestMapping(value = "/repair/delete/{id}",method = RequestMethod.GET)
    public int deleteRepairById(@PathVariable("id") int id);


    @RequestMapping(value = "/repair/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRepairByHouseId(@PathVariable("houseId") int houseId);
}
