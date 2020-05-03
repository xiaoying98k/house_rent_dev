package com.yt.houserent.houserentconsumer80.webSocket;


import com.alibaba.fastjson.JSON;
import com.api.entities.Rent;
import com.api.entities.Repair;
import com.api.houseRentService.RentServiceClient;
import com.api.houseRentService.RepairServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/webSocket/{userId}/{type}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。
    private static int onlineCount = 0;


    private  static RentServiceClient rentServiceClient;

    private static RepairServiceClient repairServiceClient;

//    public static WebSocketServer webSocketServer;

    private Session session;

//    @PostConstruct
//    public void init(){
//        webSocketServer = this;
//        webSocketServer.rentServiceClient=this.rentServiceClient;
//    }
    @Autowired
    public void setRentServiceClient(RentServiceClient rentServiceClient){
        WebSocketServer.rentServiceClient=rentServiceClient;
    }

    @Autowired
   public void setRepairServiceClient(RepairServiceClient repairServiceClient){
        WebSocketServer.repairServiceClient=repairServiceClient;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();

    /**
     * @方法描述: 开启socket
     * @return: void
     * @Author: carry
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId,@PathParam(value = "type") int type) throws ParseException {
        int maxSize = 200 * 1024;
        //  可以缓冲的传入二进制消息的最大长度
        session.setMaxBinaryMessageBufferSize(maxSize);
        //  可以缓冲的传入文本消息的最大长度
        session.setMaxTextMessageBufferSize(maxSize);
        this.session = session;
        //  加入set中
        webSockets.add(this);
        //  连接数加1
        addOnlineCount();
        //  把对应用户id的session放到sessionPool中，用于单点信息发送
        sessionPool.put(userId, session);
        System.out.println("【websocket消息】 有新连接加入！用户id" + userId + "，当前连接数为" + getOnlineCount());

        //开始向用户推送催租消息
        if (type==1){
            System.out.println(type);
            List<Rent> rentList=this.rentServiceClient.getRentListByRentId(Integer.parseInt(userId));
            if (rentList==null){
                return;
            }
            for(Rent rent:rentList){
                if (!rent.getUrge().equals("N")){//说明已被催租
                    sendOneMessage(userId,rent.getUrge());
                }
            }
        }else {//开始向房东推送物品维修申请
            System.out.println(type);
            List<Repair> repairList=this.repairServiceClient.getRepairListByOwnerId(Integer.parseInt(userId));
            if(repairList!=null){
                for (Repair repair:repairList){
                    if (repair.getState().equals("待维修")){
                        Map<String,Object> map = new HashMap<>();
                        map.put("title","物品报修申请");
                        map.put("message",repair.getGoodsDesc());
                        Date time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(repair.getTime());
                        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm");
                        map.put("time", sdf.format(time));
                        map.put("houseNumber",repair.getHouseNumber());
                        map.put("id",repair.getId());
                        sendOneMessage(userId, JSON.toJSONString(map));
                    }
                }
            }
        }


    }

    /**
     * @方法描述: 关闭socket
     * @return: void
     * @Author: carry
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        subOnlineCount();           //在线数减1
        System.out.println("【websocket消息】 连接断开！当前连接数为" + getOnlineCount());
    }

    /**
     * @方法描述: 收到客户端消息
     * @return: void
     * @Author: carry
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端消息:" + message);
    }

    /**
     * @方法描述: 广播消息全体发送
     * @return: void
     * @Author: carry
     */
    public void sendAllMessage(String message) {
        for (WebSocketServer webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @方法描述: 一对一单点消息
     * @return: void
     * @Author: carry
     */
    public synchronized boolean sendOneMessage(String userId, String message) {
        boolean flag=false;
        try {
            // 防止推送到客户端的信息太多导致弹窗太快
//            Thread.sleep(500);
            System.out.println("用户" + userId + "【websocket消息】单点消息:" + message);
            Session session = sessionPool.get(userId);
            if (session != null) {
                // getAsyncRemote是异步发送
                // 加锁防止上一个消息还未发完下一个消息又进入了此方法，防止多线程中同一个session多次被调用报错
                synchronized (session) {
                    session.getAsyncRemote().sendText(message);
                }
                flag=true;
                return flag;
            }else {
                System.out.println("session为空！");
                //用户未登录
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
    }

    /**
     * @方法描述: 发生错误时调用
     * @return: void
     * @Author: carry
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
