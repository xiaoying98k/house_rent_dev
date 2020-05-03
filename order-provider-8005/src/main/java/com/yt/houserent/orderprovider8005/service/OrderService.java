package com.yt.houserent.orderprovider8005.service;





import com.api.entities.Orders;

import java.util.List;

public interface OrderService {
    public int addOrder(Orders order);
    public int deleteOrderById(int id);
    public int deleteOrderByHouseId(int houseId);
    public List<Orders> findAll();
    public Orders findOrderById(int id);
    public List<Orders> searchListByRentId(int rentId);
    public List<Orders> searchListByOwnerId(int ownerId);
    public int updateByVO(Orders order);
}
