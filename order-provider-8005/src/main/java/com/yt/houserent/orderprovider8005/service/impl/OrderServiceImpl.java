package com.yt.houserent.orderprovider8005.service.impl;


import com.api.entities.Orders;
import com.yt.houserent.orderprovider8005.dao.OrderDao;
import com.yt.houserent.orderprovider8005.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public int addOrder(Orders order) {
        return orderDao.addOrder(order);
    }

    @Override
    public int deleteOrderById(int id) {
        return orderDao.deleteOrderById(id);
    }

    @Override
    public int deleteOrderByHouseId(int houseId) {
        return orderDao.deleteOrderByHouseId(houseId);
    }

    @Override
    public List<Orders> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Orders findOrderById(int id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public List<Orders> searchListByRentId(int rentId) {
        return orderDao.searchListByRentId(rentId);
    }

    @Override
    public List<Orders> searchListByOwnerId(int ownerId) {
        return orderDao.searchListByOwnerId(ownerId);
    }

    @Override
    public int updateByVO(Orders order) {
        return orderDao.updateByVO(order);
    }
}
