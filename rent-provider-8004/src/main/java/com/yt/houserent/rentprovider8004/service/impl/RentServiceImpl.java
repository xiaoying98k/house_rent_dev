package com.yt.houserent.rentprovider8004.service.impl;

import com.api.entities.Rent;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.yt.houserent.rentprovider8004.dao.RentDao;
import com.yt.houserent.rentprovider8004.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RentServiceImpl implements RentService {
    @Autowired
    private RentDao  rentDao;
    @Override

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public int addRent(Rent rent) {
        return rentDao.addRent(rent);
    }

    @Override
    public int deleteRentById(int id) {
        return rentDao.deleteRentById(id);
    }

    @Override
    public int deleteRentByHouseId(int houseId) {
        return rentDao.deleteRentByHouseId(houseId);
    }

    @Override
    public List<Rent> findAll() {
        return rentDao.findAll();
    }

    @Override
    public Rent findRentById(int id) {
        return rentDao.findRentById(id);
    }

    @Override
    public List<Rent> searchListByRentId(int rentId) {
        return rentDao.searchListByRentId(rentId);
    }

    @Override
    public List<Rent> searchListByOwnerId(int ownerId) {
        return rentDao.searchListByOwnerId(ownerId);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public int updateByVO(Rent rent) {
        return rentDao.updateByVO(rent);
    }
}
