package com.jdrj.common.interceptor;

import cn.hutool.core.util.RandomUtil;
import com.jdrj.common.interceptor.token.JWTUtils;
import com.jdrj.common.interceptor.token.Token;
import com.jdrj.common.interceptor.token.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
	
	public static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		
		//创建token
//		Token token = Token.geTokenManager().createToken(TokenConfig.SECRET, TokenConfig.SERVER_TOKEN_EXPIRE_TIME);
		//创建token
		String key = RandomUtil.simpleUUID();
		Token token = new Token();
		token.setKey(key);
		//设置过期时间为60秒
		token.setExpireTime(TokenConfig.CLIENT_TOKEN_EXPIRE_TIME);
		
		String authorization = JWTUtils.create(token.getKey(), token.getExpireTime(), TokenConfig.SECRET);
		
		String result = String.format("{\"code\":200,\"data\":\"{\"authorization\":\"%s\"}\"}", authorization);
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Authorization", authorization);
		
		response.getWriter().write(result);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}	
}