package com.yt.houserent.repairprovider8003.service;


import com.api.entities.Repair;

import java.util.List;

public interface RepairService {
    public int addRepair(Repair repair);
    public int deleteRepairById(int id);
    public int deleteRepairByHouseId(int houseId);
    public List<Repair> findAll();
    public Repair findRepairById(int id);
    public List<Repair> searchListByRentId(int rentId);
    public List<Repair> searchListByOwnerId(int ownerId);
    public int updateByVO(Repair repair);
}
