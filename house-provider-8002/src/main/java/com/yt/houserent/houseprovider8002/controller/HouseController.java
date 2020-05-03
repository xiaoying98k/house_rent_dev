package com.yt.houserent.houseprovider8002.controller;

import com.api.entities.House;
import com.yt.houserent.houseprovider8002.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HouseController {
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "/House/add",method = RequestMethod.POST)
    public int add(@RequestBody House house){
        return houseService.addHouse(house);
    }

    @RequestMapping(value = "/House/get/{ownerId}/{renting}",method = RequestMethod.GET)
    public List<House> getHouse(@PathVariable("ownerId") int ownerId,@PathVariable("renting") int renting){
        return houseService.findHouseByOwnerId(ownerId,renting);
    }

    @RequestMapping(value = "/House/doSearch",method = RequestMethod.POST)
    public List<House> getHouseList(@RequestBody House house){
        return houseService.searchListByVO(house);
    }

    @RequestMapping(value = "/House/findHouseBySearch",method = RequestMethod.POST)
    public List<House> findHouseBySearch(@RequestBody Map searchMap){

        return houseService.findHouseBySearch(searchMap);
    }


    /**
     * @Description:模糊查询
     * @Param: [locationAndNumber]
     * @return: java.util.List<com.api.entities.House>
     * @Author: yantao5524@163.com
     * @Date: 2020-04-24
     */
    @RequestMapping(value = "/House/fuzzySearch",method = RequestMethod.POST)
    public List<House> fuzzySearch(@RequestBody String locationAndNumber){

        return houseService.fuzzySearch(locationAndNumber);
    }

    @RequestMapping(value = "/House/update",method = RequestMethod.POST)
    public int updateHouse(@RequestBody House house){
        return houseService.updateByVO(house);
    }

    @RequestMapping(value = "/House/delete/{id}",method = RequestMethod.GET)
    public int deleteHouse(@PathVariable("id") int id){
        return houseService.deleteHouseById(id);
    }


    @RequestMapping(value = "/House/findHouseById/{id}",method = RequestMethod.GET)
    public House findHouseById(@PathVariable("id") int id){
        return houseService.findHouseById(id);
    }
}

