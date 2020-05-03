package com.yt.houserent.userprovider8001.service;

import com.api.entities.User;
import com.api.utils.BackEndResp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    public int addUser(User user);
    public int deleteUserById(int id);
    public User findUserById(int id);
    public List<User> findAll();
    public List<User> searchListByVO(User user);
    public int updateByVO(User user);
    public BackEndResp pay(int ownerId, int rentId, BigDecimal money);
}
