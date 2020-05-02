package com.jdrj.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = SocketConfig.PREFIX)
public class SocketConfig {
	public static final String PREFIX = "socket";
	
	private String host;
	private Integer port;
	
}

