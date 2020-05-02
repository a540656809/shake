package com.jdrj.common.interceptor;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.jdrj.common.interceptor.token.Token;
import com.jdrj.common.interceptor.token.TokenConfig;
import com.jdrj.common.interceptor.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenInterceptor implements HandlerInterceptor {

	public static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从header中获取authorization
		String authorization = request.getHeader(Token.TOKEN_HEADER);
		
		logger.info("请求的authorization: {}", authorization);
		
		if (StringUtils.isBlank(authorization)) {
			String result = String.format("{\"code\":%d,\"message\":\"Unauthorized\"}", 401);
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(401);
			response.getWriter().write(result);
			return false;
		}
		//通过jwt创建token
		Token token = Token.create(authorization, TokenConfig.SECRET);

		if (null == token) {
			String result = String.format("{\"code\":%d,\"message\":\"Unauthorized\"}", 401);
			logger.info("token已经过期");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(401);
			response.getWriter().write(result);
			return false;
		}
		// 将token信息保存进 本地线程中
		TokenUtils.set(token);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 更新token
		Token token = TokenUtils.get();
		//如果有数据 需要存储再调用存储
		if (null != token.isStore() && token.isStore()) {
			Date date = token.getExpireTime();
			Token.geTokenManager().saveToken(TokenUtils.getAndRemove(), (int) DateUtil.between(new Date(), date, DateUnit.SECOND) + 5);
		}
	}
}