//package com.bootdo.common.config;
//
//import com.bootdo.common.interceptor.LoginInterceptor;
//import com.bootdo.common.interceptor.TokenInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 不能继承 WebMvcConfigurationSupport， 继承 WebMvcConfigurationSupport
// * 后会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置！从而导致所有的Date返回都变成时间戳！
// * @see https://blog.csdn.net/qq_30912043/article/details/80967352
// *
// */
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//
//		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/mp/**").addPathPatterns("/app/**").order(1);
//
//		List<String> patterns = new ArrayList<>();
//		patterns.add("/mp/**");
//		registry.addInterceptor(new LoginInterceptor()).addPathPatterns(patterns).order(2);
//
//		WebMvcConfigurer.super.addInterceptors(registry);
//	}
//
//}
