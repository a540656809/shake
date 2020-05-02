package com.jdrj.socket.server;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ServerRunner {

	private final SocketIOServer server;

	@Autowired
	public ServerRunner(SocketIOServer server) {
		this.server = server;
	}

	@PostConstruct
	public void startServer() {
		server.start();
	}

	@PreDestroy
	public void stopServer() {
		server.stop();
	}
}
