package com.yt.houserent.rentprovider8004.dao;


import com.api.entities.Rent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentDao {

    public int addRent(Rent rent);
    public int deleteRentById(int id);
    public int deleteRentByHouseId(int houseId);
    public List<Rent> findAll();
    public Rent findRentById(int id);
    public List<Rent> searchListByRentId(int rentId);
    public List<Rent> searchListByOwnerId(int ownerId);
    public int updateByVO(Rent rent);
}
