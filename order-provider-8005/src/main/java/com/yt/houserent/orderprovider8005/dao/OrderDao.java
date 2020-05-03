package com.yt.houserent.orderprovider8005.dao;



import com.api.entities.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    public int addOrder(Orders order);
    public int deleteOrderById(int id);
    public int deleteOrderByHouseId(int houseId);
    public List<Orders> findAll();
    public Orders findOrderById(int id);
    public List<Orders> searchListByRentId(int rentId);
    public List<Orders> searchListByOwnerId(int ownerId);
    public int updateByVO(Orders order);
}
