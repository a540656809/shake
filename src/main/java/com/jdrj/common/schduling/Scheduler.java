package com.jdrj.common.schduling;

import com.jdrj.socket.controller.MessageController;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class Scheduler{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    private SocketIOServer server;

    //每隔0.5秒执行一次
    @Scheduled(fixedRate = 500)
    public void testTasks() {
        if(1 == MessageController.status){
            int shake = server.getRoomOperations("shake").getClients().size();
            System.out.println("size=" + shake);
            if(shake > 0){
                server.getBroadcastOperations().sendEvent("chatevent", MessageController.map);
            }
        }
    }



}