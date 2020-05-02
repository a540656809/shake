package com.jdrj.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private  String appid;
    private  String secret;
    private  String aesKey;
    private  String token;
}