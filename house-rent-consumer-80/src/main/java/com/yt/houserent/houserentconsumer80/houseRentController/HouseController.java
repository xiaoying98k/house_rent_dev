package com.yt.houserent.houserentconsumer80.houseRentController;

import com.alibaba.fastjson.JSON;
import com.api.entities.House;
import com.api.houseRentService.HouseServiceClient;
import com.api.utils.BackEndResp;
import com.api.utils.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HouseController {
    @Autowired
    private HouseServiceClient houseServiceClient;
    protected static final Logger log= LoggerFactory.getLogger(HouseController.class);

    //房主添加房源信息
    @RequestMapping(value = "/house/add",method = RequestMethod.POST)
    public Boolean add(House house,@RequestParam("file") MultipartFile file){
        Boolean flag=false;
        log.info("开始调用存储图片方法");
        FileUpload fileUpload=new FileUpload();
        String imageURL=fileUpload.uploadFile(file,house.getOwnerId());
        if (imageURL!=null){
            house.setImage(imageURL);
            flag=true;
        }else {
            log.info("保存文件失败");
            return flag;
        }
        log.info("房主id:{},开始添加房源信息{}",house.getOwnerId(),house.toString());
        try{
            int count=this.houseServiceClient.add(house);
            if (count>0){
                log.info("房主id:{},添加房源成功",house.getOwnerId());
                flag=true;
            }else {
                log.info("房主id:{},添加房源失败",house.getOwnerId());
            }
        }catch (Exception e){
            log.error("房主id:{},添加房源出错",house.getOwnerId());
        }
        return flag;
    }

    @RequestMapping(value = "/house/get/{ownerId}/{type}",method = RequestMethod.GET)
    public BackEndResp getHouse(@PathVariable("ownerId") int ownerId,@PathVariable("type") int type){
        log.info("房主搜索自己发布的所有房源信息:"+type);
        BackEndResp backEndResp=BackEndResp.build();
        List<House> houseList=new ArrayList<House>();
        try{
            //type:  0.查询所有  1.查询待出租的   2，查询已出租的
          houseList=this.houseServiceClient.getHouse(ownerId,type);
            backEndResp.setRespCode(backEndResp.SUCCESS);
            backEndResp.setData(houseList);
        }catch (Exception e){
            log.info("搜索发布的所有房源信息出错");
            backEndResp.setRespCode(backEndResp.FAIL);
        }


        return backEndResp;
    }

    //根据条件搜索房源
    @RequestMapping(value = "/house/doSearch",method = RequestMethod.POST)
    public List<House> getHouseList(@RequestBody House house){
        log.info("根据条件搜索房源");
        List<House> houseList=new ArrayList<House>();
        try {
             houseList=this.houseServiceClient.getHouseList(house);
            log.info("一共搜素到{}条数据",houseList.size());
        }catch (Exception e){
            log.error("搜索房源失败");
        }
        return houseList;
    }

    @RequestMapping(value = "/house/findHouseBySearch",method = RequestMethod.POST)
    public List<House> findHouseBySearch(@RequestBody Map searchMap){
        log.info("根据条件搜索房源");
        log.info(searchMap.get("minPrice").toString());
        List<House> houseList=new ArrayList<House>();
        try {
            houseList=this.houseServiceClient.findHouseBySearch(searchMap);
            log.info("一共搜素到{}条数据",houseList.size());
        }catch (Exception e){
            log.error("搜索房源失败");
        }
        return houseList;
    }

    @RequestMapping(value = "/house/fuzzySearch",method = RequestMethod.POST)
    public List<House> fuzzySearch(@RequestBody Map<String,String> searchMap){
        String locationAndNumber=searchMap.get("locationAndNumber");
        log.info("模糊搜索房源:"+locationAndNumber);
        List<House> houseList=new ArrayList<House>();
        try {
            houseList=this.houseServiceClient.fuzzySearch(locationAndNumber);
            log.info("一共搜素到{}条数据",houseList.size());
        }catch (Exception e){
            e.printStackTrace();
            log.error("搜索房源失败");
        }
        return houseList;
    }

    @RequestMapping(value = "/house/update",method = RequestMethod.POST)
    public BackEndResp updateHouse(House house,@RequestParam("file") MultipartFile file){
        log.info("房主开始修改房源信息");
        BackEndResp backEndResp=BackEndResp.build();
        log.info("开始调用存储图片方法");
        FileUpload fileUpload=new FileUpload();
        String imageURL=fileUpload.uploadFile(file,house.getOwnerId());
        if (imageURL!=null){
            house.setImage(imageURL);
        }else {
            log.info("保存文件失败");
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("保存图片文件失败");
            return backEndResp;
        }
        log.info("房主id:{},开始修改房源信息{}",house.getOwnerId(),house.toString());
        try{
            int count=this.houseServiceClient.updateHouse(house);
            if (count>0){
                log.info("房主id:{},修改房源成功",house.getOwnerId());
                backEndResp.setRespCode(backEndResp.SUCCESS);
                backEndResp.setRespMsg("修改房源信息成功");
            }else {
                log.info("房主id:{},修改房源失败",house.getOwnerId());
                backEndResp.setRespCode(backEndResp.FAIL);
                backEndResp.setRespMsg("修改房源信息失败");
            }
        }catch (Exception e){
            log.error("房主id:{},修改房源出错",house.getOwnerId());
            backEndResp.setRespCode(backEndResp.FAIL);
            backEndResp.setRespMsg("后台修改房源信息出错");
        }
        return backEndResp;
    }

    @RequestMapping(value = "/house/delete/{id}",method = RequestMethod.GET)
    public int deleteHouse(@PathVariable("id") int id){
        return this.houseServiceClient.deleteHouse(id);
    }
}
