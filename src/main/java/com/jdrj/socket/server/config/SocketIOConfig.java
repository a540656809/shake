package com.jdrj.socket.server.config;

import com.jdrj.common.config.RedisConfig;
import com.jdrj.common.config.SocketConfig;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;

@Configuration
public class SocketIOConfig {

	@Autowired
	private SocketConfig socketConfig;
	
	@Autowired
	private RedisConfig redis;
	
	
	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(socketConfig.getHost());
		config.setPort(socketConfig.getPort());
		config.setTransports(Transport.POLLING);
		config.setAuthorizationListener(new AuthorizationListener() {

			@Override
			public boolean isAuthorized(HandshakeData arg0) {
				return true;
			}

		});
		
		RedissonClient redisson = redisson();
		
        RedissonStoreFactory clientStoreFactory = new RedissonStoreFactory(redisson);
        config.setStoreFactory(clientStoreFactory);

		final SocketIOServer server = new SocketIOServer(config);

//		server.addNamespace("/game");
		return server;
	}
	
	@Bean(name = "redissonClient")
	public RedissonClient redisson() {
		//配置redis 作为store factory
		//开发时展示不使用redis
		Config redisCofing = new Config();
		String redisAddress = String.format("redis://%s:%s", redis.getHost(), redis.getPort());
		redisCofing.useSingleServer()
			.setConnectionMinimumIdleSize(20)
			.setAddress(redisAddress)
			.setPassword(redis.getPassword())
			.setConnectionPoolSize(1000)
			.setTimeout(5000)
			.setConnectTimeout(5000);
        
        RedissonClient redisson = Redisson.create(redisCofing);
        
        return redisson;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
}
