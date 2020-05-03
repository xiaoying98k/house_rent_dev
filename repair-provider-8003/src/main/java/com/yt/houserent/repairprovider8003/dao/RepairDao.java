package com.yt.houserent.repairprovider8003.dao;

import com.api.entities.Repair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RepairDao {
    public int addRepair(Repair repair);
    public int deleteRepairById(int id);
    public int deleteRepairByHouseId(int houseId);
    public List<Repair> findAll();
    public Repair findRepairById(int id);
    public List<Repair> searchListByRentId(int rentId);
    public List<Repair> searchListByOwnerId(int ownerId);
    public int updateByVO(Repair repair);
}
