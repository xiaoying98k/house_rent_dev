package com.yt.houserent.rentprovider8004.service;


import com.api.entities.Rent;

import java.util.List;

public interface RentService {
    public int addRent(Rent rent);
    public int deleteRentById(int id);
    public int deleteRentByHouseId(int houseId);
    public List<Rent> findAll();
    public Rent findRentById(int id);
    public List<Rent> searchListByRentId(int rentId);
    public List<Rent> searchListByOwnerId(int ownerId);
    public int updateByVO(Rent rent);
}
