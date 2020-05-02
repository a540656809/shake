package com.jdrj.socket.controller;

import com.jdrj.socket.server.pojo.ChatObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MessageController {

	//key -> ssessionId value -> INIT
	public static final String DEFAULT_VALUE = "INIT";

	public static Map<String, String> root = new HashMap<>(256);
	public static Map<String, Integer> map = new ConcurrentHashMap<>(256);

	public static Integer status = 0;//0未开始 1进行中 2已结束

	@Autowired
	private SocketIOServer server;
	

	// 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
	@OnEvent(value = "chatevent")
	public void chatevent(SocketIOClient client, AckRequest request, ChatObject data) {
		String sessionId = client.getSessionId().toString();
		if(DEFAULT_VALUE.equals(root.get(sessionId))){
			//如果用户是初始化连接进来
			//标记用户已在房间中 且是连接状态 把用户的这个分数搞为0
			root.put(sessionId, data.getUserName());
			map.put(data.getUserName(), 0);
		}else{
			if(MessageController.status == 1){
				//摇晃一次服务器+1
				map.put(data.getUserName(), map.get(data.getUserName()) + 1);
				//性能优化
//				server.getBroadcastOperations().sendEvent("chatevent", map);
			}else if(MessageController.status == 0){
				client.sendEvent("clear", true);
			}else if(MessageController.status == 2){
				client.sendEvent("end", true);
			}
		}
	}


}
