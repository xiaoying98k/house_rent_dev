package com.yt.houserent.houseprovider8002.dao;

import com.api.entities.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseDao {
    public int addHouse(House house);
    public int  deleteHouseById(int id);
    public List<House> findHouseByOwnerId(int ownerId,int renting);
    public House findHouseById(int id);
    public List<House> findAll();
    public List<House> searchListByVO(House house);
    public int updateByVO(House house);
    public List<House> findHouseBySearch(@Param("searchMap")Map searchMap);
    public List<House> fuzzySearch(@Param("locationAndNumber")String locationAndNumber);
}
