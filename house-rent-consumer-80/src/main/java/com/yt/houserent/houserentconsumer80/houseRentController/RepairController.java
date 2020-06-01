package com.yt.houserent.houserentconsumer80.houseRentController;

import com.alibaba.fastjson.JSON;
import com.api.entities.Repair;
import com.api.houseRentService.RepairServiceClient;
import com.api.utils.BackEndResp;
import com.yt.houserent.houserentconsumer80.webSocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RepairController {
    @Autowired
    private RepairServiceClient repairServiceClient;

    @Autowired
    private WebSocketServer webSocketServer;

    protected static final Logger log= LoggerFactory.getLogger(RepairController.class);
    @RequestMapping(value = "/repair/add",method = RequestMethod.POST)
    public BackEndResp add(@RequestBody Repair repair){
        repair.setState("待维修");
        int key=this.repairServiceClient.add(repair);
        log.info("增加的主键key:"+key);
        BackEndResp backEndResp=BackEndResp.build();
        log.info("用户提交维修申请");
        if (key>0){
            log.info("系统已成功保存维修记录id:"+key);
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("维修申请提交成功");
            Map<String,Object> map=new HashMap<>();
            map.put("title","物品报修申请");
            map.put("message",repair.getGoodsDesc());
            map.put("houseNumber",repair.getHouseNumber());
            map.put("id",key);
            map.put("time",new SimpleDateFormat("MM-dd hh:mm").format(new Date()));
            webSocketServer.sendOneMessage(String.valueOf(repair.getOwnerId()), JSON.toJSONString(map));
        }else {
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("抱歉,维修提交申请失败");
        }
        return backEndResp;
    }

    @RequestMapping(value = "/repair/get/{id}",method = RequestMethod.GET)
    public Repair getRepair(@PathVariable("id") int id){
        return this.repairServiceClient.getRepair(id);
    }

    @RequestMapping(value = "/repair/doSearchByOwnerId/{ownerId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByOwnerId(@PathVariable("ownerId") int ownerId){
        return this.repairServiceClient.getRepairListByOwnerId(ownerId);
    }

    @RequestMapping(value = "/repair/doSearchByRentId/{rentId}",method = RequestMethod.GET)
    public List<Repair> getRepairListByRentId(@PathVariable("rentId") int rentId){
        return this.repairServiceClient.getRepairListByRentId(rentId);
    }

    @RequestMapping(value = "/repair/update",method = RequestMethod.POST)
    public int updateRepair(@RequestBody Repair repair){
        return this.repairServiceClient.updateRepair(repair);
    }

    //房主提交物品维修结果
    @RequestMapping(value = "/repair/updateById/{id}",method = RequestMethod.GET)
    public BackEndResp updateRepairById(@PathVariable("id") int id){
        BackEndResp backEndResp=BackEndResp.build();
        Repair repair=new Repair().setId(id).setState("已处理");
        int count=this.repairServiceClient.updateRepair(repair);
        if (count>0){
            log.info("成功提交处理状态信息");
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setRespMsg("成功修改维修状态信息");
        }else {
            log.info("提交处理状态信息失败");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("抱歉,修改维修状态信息失败,可线下告知租户");
        }
        return backEndResp;
    }

    @RequestMapping(value = "/repair/delete/{id}",method = RequestMethod.GET)
    public int deleteRepairById(@PathVariable("id") int id){
        return this.repairServiceClient.deleteRepairById(id);
    }

    @RequestMapping(value = "/repair/deleteByHouseId/{houseId}",method = RequestMethod.GET)
    public int deleteRepairByHouseId(@PathVariable("houseId") int houseId){
        return this.repairServiceClient.deleteRepairByHouseId(houseId);
    }
}
