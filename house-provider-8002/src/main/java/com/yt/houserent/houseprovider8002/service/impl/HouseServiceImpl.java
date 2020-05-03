package com.yt.houserent.houseprovider8002.service.impl;

import com.api.entities.House;
import com.yt.houserent.houseprovider8002.dao.HouseDao;
import com.yt.houserent.houseprovider8002.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {
@Autowired
private HouseDao houseDao;
    @Override
    public int addHouse(House house) {
        return houseDao.addHouse(house);
    }

    @Override
    public int deleteHouseById(int id) {
        return houseDao.deleteHouseById(id);
    }

    @Override
    public List<House> findHouseByOwnerId(int ownerId,int renting) {
        return houseDao.findHouseByOwnerId(ownerId,renting);
    }

    @Override
    public House findHouseById(int id) {
        return houseDao.findHouseById(id);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }

    @Override
    public List<House> searchListByVO(House house) {
        return houseDao.searchListByVO(house);
    }

    @Override
    public int updateByVO(House house) {
        return houseDao.updateByVO(house);
    }

    @Override
    public List<House> findHouseBySearch(Map searchMap) {
        return houseDao.findHouseBySearch(searchMap);
    }

    @Override
    public List<House> fuzzySearch(String locationAndNumber) {
        return houseDao.fuzzySearch(locationAndNumber);
    }
}
