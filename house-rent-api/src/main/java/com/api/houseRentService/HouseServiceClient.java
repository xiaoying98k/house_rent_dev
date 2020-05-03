package com.api.houseRentService;

import com.api.entities.House;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "house-provider")
public interface HouseServiceClient {
    @RequestMapping(value = "/House/add",method = RequestMethod.POST)
    public int add(@RequestBody House house);

    @RequestMapping(value = "/House/get/{ownerId}/{renting}",method = RequestMethod.GET)
    public List<House> getHouse(@PathVariable("ownerId") int ownerId,@PathVariable("renting") int renting);

    @RequestMapping(value = "/House/doSearch",method = RequestMethod.POST)
    public List<House> getHouseList(@RequestBody House house);

    @RequestMapping(value = "/House/findHouseBySearch",method = RequestMethod.POST)
    public List<House> findHouseBySearch(@RequestBody Map searchMap);

    @RequestMapping(value = "/House/fuzzySearch",method = RequestMethod.POST)
    public List<House> fuzzySearch( String locationAndNumber);

    @RequestMapping(value = "/House/update",method = RequestMethod.POST)
    public int updateHouse(@RequestBody House house);

    @RequestMapping(value = "/House/delete/{id}",method = RequestMethod.GET)
    public int deleteHouse(@PathVariable("id") int id);

    @RequestMapping(value = "/House/findHouseById/{id}",method = RequestMethod.GET)
    public House findHouseById(@PathVariable("id") int id);
}
