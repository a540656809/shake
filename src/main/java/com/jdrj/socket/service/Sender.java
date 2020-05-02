package com.jdrj.socket.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Sender {
	@Autowired
	private PubSubStore store;
	
	@Autowired
	private SocketIOServer server;
	
	/**
	 * 发送消息
	 * @param sessionId
	 * @param event
	 * @param data
	 */
	public void send(String sessionId, String event, Object data) {
		Packet packet = new Packet(PacketType.MESSAGE);
		packet.setSubType(PacketType.EVENT);
		packet.setName(event);
		packet.setData(data);
		
		store.publish(PubSubType.DISPATCH, new DispatchMessage(sessionId, packet, ""));
	}
	
	/**
	 * 发送消息
	 * @param client
	 * @param sessionId
	 * @param event
	 * @param data
	 */
	public void send(SocketIOClient client, String sessionId, String event, Object data) {
		
		if (null != client && client.getSessionId().toString().equals(sessionId)) {
			client.sendEvent(event, data);
			return;
		}
		
		SocketIOClient socketClient = server.getClient(UUID.fromString(sessionId));  //从本地内存中获取用户client
		
		if (null == socketClient) {
			//调用远程发送
			send(sessionId, event, data);
			return;
		} 
			
		socketClient.sendEvent(event, data);
	}
	
	/**
	 * 发送消息
	 * @param client
	 * @param event
	 * @param data
	 */
	public void send(SocketIOClient client, String event, Object data){
		client.sendEvent(event, data);
	}
	
	
	
}
