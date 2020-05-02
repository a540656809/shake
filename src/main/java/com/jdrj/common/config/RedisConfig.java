package com.jdrj.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = RedisConfig.PREFIX)
public class RedisConfig {
	public static final String PREFIX = "redis";
	
	private String host = "127.0.0.1";
	private Integer port = 6379;
	private String password;
	
}

