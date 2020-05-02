package com.jdrj.common.interceptor;

import com.jdrj.common.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RequestParamsInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(RequestParamsInterceptor.class);
	
	private long startTime = 0L;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		startTime = System.currentTimeMillis();
		logger.info("============== request ================"); 
		logger.info("request ip:{}", IPUtils.getIpAddr(request));
		logger.info("url:{}", request.getRequestURL());
		logger.info("method:{}", request.getMethod());
		
		Map<String, String[]> params = request.getParameterMap();
		
		for (Map.Entry<String, String[]> map : params.entrySet()){
			logger.info("params {}:{}", map.getKey(), map.getValue()); 
		}
		
		return true;
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
		logger.info("startTime:{}", startTime);
		long endTime = System.currentTimeMillis();
		logger.info("endTime:{}", endTime);
		logger.info("cost:{}", endTime - startTime);
	}  
}