package com.yt.houserent.houseprovider8002.service;

import com.api.entities.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HouseService {
    public int addHouse(House house);
    public int  deleteHouseById(int id);
    public List<House> findHouseByOwnerId(int ownId,int renting);
    public House findHouseById(int id);
    public List<House> findAll();
    public List<House> searchListByVO(House house);
    public int updateByVO(House house);
    public List<House> findHouseBySearch(Map searchMap);
    public List<House> fuzzySearch(String locationAndNumber);

}
