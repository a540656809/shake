package com.jdrj.socket.server.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化 PubSubStore
 *
 */
@Configuration
public class PubSubStoreConfig {

	@Autowired
	private SocketIOServer server;
	
	@Bean
	public PubSubStore store() {
		final PubSubStore pubSubStore = server.getConfiguration().getStoreFactory().pubSubStore();
		return pubSubStore;
	}
}
