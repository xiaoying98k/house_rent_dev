package com.api.houseRentService;

import com.api.entities.User;
import com.api.utils.BackEndResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value="user-provider")
public interface UserServiceClient {
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public int add(@RequestBody User user);

    @RequestMapping(value = "/user/get/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id);

    @RequestMapping(value = "/user/doSearch",method = RequestMethod.POST)
    public List<User> getUserList(@RequestBody User user);

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public int updateUser(@RequestBody User user);

    @RequestMapping(value = "/user/pay",method = RequestMethod.POST)
    public BackEndResp pay(@RequestBody String payJson);

    @RequestMapping(value = "/user/delete/{id}",method = RequestMethod.GET)
    public int deleteUser(@PathVariable("id") int id);
}
