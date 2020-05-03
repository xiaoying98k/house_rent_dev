package com.yt.houserent.userprovider8001.dao;

import com.api.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserDao {
    public int addUser(User user);
    public int deleteUserById(int id);
    public User findUserById(int id);
    public List<User> findAll();
    public List<User> searchListByVO(User user);
    public int updateByVO(User user);
    public int updateForPay(@Param("payee") int payeeId, @Param("payer") int payerId, @Param("money")BigDecimal money);

}
