package com.jdrj.service;

import com.jdrj.socket.controller.MessageController;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
	


	/**
	 * 绑定用户
	 * @param client
	 */
	public void bind(SocketIOClient client) {
		//刷新页面 重连的时候 用户切换
		//连接或者重连的时候 sessionId更新
		String sessionId = client.getSessionId().toString();
		List<String> name = client.getHandshakeData().getUrlParams().get("name");
		MessageController.root.put(sessionId, name.get(0));
		MessageController.map.put(name.get(0), 0);
		if("admin".equals(name.get(0))){
			MessageController.map.put(name.get(0), -1);
		}
		client.joinRoom("shake");
	}
	
	/**
	 * 解绑用户
	 * @param client
	 */
	public void unbind(SocketIOClient client) {
		String sessionId = client.getSessionId().toString();
		try{
			MessageController.map.remove(MessageController.root.get(sessionId));
			MessageController.root.remove(sessionId);
		}catch (NullPointerException e){
			//不处理
		}
	}
	

}
