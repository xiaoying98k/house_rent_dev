package com.yt.houserent.repairprovider8003.service.impl;

import com.api.entities.Repair;
import com.yt.houserent.repairprovider8003.dao.RepairDao;
import com.yt.houserent.repairprovider8003.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairDao repairDao;
    @Override
    public int addRepair(Repair repair) {
        return repairDao.addRepair(repair);
    }

    @Override
    public int deleteRepairById(int id) {
        return repairDao.deleteRepairById(id);
    }

    @Override
    public int deleteRepairByHouseId(int houseId) {
        return repairDao.deleteRepairByHouseId(houseId);
    }

    @Override
    public List<Repair> findAll() {
        return repairDao.findAll();
    }

    @Override
    public Repair findRepairById(int id) {
        return repairDao.findRepairById(id);
    }

    @Override
    public List<Repair> searchListByRentId(int rentId) {
        return repairDao.searchListByRentId(rentId);
    }

    @Override
    public List<Repair> searchListByOwnerId(int ownerId) {
        return repairDao.searchListByOwnerId(ownerId);
    }

    @Override
    public int updateByVO(Repair repair) {
        return repairDao.updateByVO(repair);
    }
}
