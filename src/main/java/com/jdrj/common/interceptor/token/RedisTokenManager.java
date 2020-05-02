package com.jdrj.common.interceptor.token;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdrj.common.utils.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过 Redis 存储和验证 token 的实现类
 *- @author xiaobin
 *
 */
public class RedisTokenManager implements TokenManager {

	private static final Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);

	private static final String PREFIX = "token:";


	@Override
	public Token createToken (String secret, int timeout) {
		String key = RandomUtil.simpleUUID();

		Token token = new Token();
		token.setKey(key);
		token.setExpireTime();

		if (timeout <= 0) {
			timeout = Token.EXPIRE_TIME;
		}

		JedisUtil.setEx(PREFIX + key, token.toString(), timeout);
		return token;
	}

	@Override
	public boolean checkToken(Token token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Token getToken (String authorization, String secret) {
		String key = JWTUtils.getToken(authorization, secret);

		if (StringUtils.isEmpty(key)) {
			return null;
		}

//		if (!JedisUtil.exists(PREFIX + key)) {
//			return null;
//		}

		String result = JedisUtil.get(PREFIX + key);

		if (result == null) {
			return null;
		}

		JSONObject obj = JSON.parseObject(result);

		if (obj == null) {
			return null;
		}

		Token token = new Token();
		token.setObject(obj);
		token.setKey(key);
		return token;
	}

	@Override
	public Token getToken(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}

		String result = JedisUtil.get(PREFIX + key);

		if (result == null) {
			return null;
		}

		JSONObject obj = JSON.parseObject(result);

		if (obj == null) {
			return null;
		}

		Token token = new Token();
		token.setObject(obj);
		token.setKey(key);
		return token;
	}

	@Override
	public void deleteToken(String key) {
		JedisUtil.del(PREFIX + key);
	}

	@Override
	public void saveToken(Token token, int timeout) {
		String key = token.getKey();
		logger.info(token.toString());
		token.setExpireTime();

		if (timeout <= 0) {
			timeout = Token.EXPIRE_TIME;
		}

		JedisUtil.setEx(PREFIX + key, token.toString(), timeout);
	}


}
