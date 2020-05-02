package com.jdrj.common.interceptor.token;

public class TokenConfig {
	public static final String AES_SECRET = "4cd3dc41bb6ced32";
	public static final String SECRET = "4cd3dc41bb6ced32d523ec9f0e0686d5";  //加密秘钥
	public static final Boolean IS_STORE = false;
	public static final int CLIENT_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 30; //60分钟   客户端单个Token的超时时间 设置较短的时间比较好
	public static final int SERVER_TOKEN_EXPIRE_TIME = 60 * 60; //60分钟   服务器端Token过期时间 
}
