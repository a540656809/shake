package com.jdrj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
//关掉db配置
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableCaching
@EnableScheduling
public class ShakeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShakeApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ启动成功 ヾ(◍°∇°◍)ﾉ");
    }
}
