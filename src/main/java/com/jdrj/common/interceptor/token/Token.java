package com.jdrj.common.interceptor.token;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Token {

	private static final Logger logger = LoggerFactory.getLogger(Token.class);

	public static final int EXPIRE_TIME = 60; //60分钟
	public static String TOKEN_HEADER = "Authorization";

	public static final String EXPIRE = "_expire";
	public static final String STORE = "_store";
	public static final String USER_KEY = "_user_key";
	public static final String REMOTE = "_remote";
	
	/**
	 * 通过jwt创建token
	 * @param authorization
	 * @param secret
	 * @return
	 */
	public static Token create(String authorization, String secret) {
		DecodedJWT jwt = JWTUtils.verifier(authorization, secret);
		if (null == jwt) {
			return null;
		}
		
		Token token = new Token();
		token.set(EXPIRE, jwt.getExpiresAt().getTime());
		//获取jwt中的key
		String key = jwt.getId();
		token.setKey(key);
		//获取jwt中的token
		String userToken = JWTUtils.getToken(jwt);
		if (StringUtils.isNotEmpty(userToken)) {
			userToken = AESUtil.decrypt(userToken, TokenConfig.AES_SECRET);
			token.setUserKey(Long.parseLong(userToken));
		}
		
		return token;
	}

	private JSONObject obj = new JSONObject();

	/**
	 * token 对应的唯一key
	 */
	private String key;

	/**
	 * 设置值
	 * 
	 * @param key
	 * @param value
	 * @return this
	 */
	public Token set(String key, Object value) {
		obj.put(key, value);
		return this;
	}

	public Token remove(String key) {
		obj.remove(key);
		return this;
	}

	public Token setObject(JSONObject obj) {
		this.obj = obj;
		return this;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param second
	 * @return this
	 */
	public Token setExpireTime(int second) {
		Date date = DateUtil.offset(new Date(), DateField.SECOND, second);
		return this.set(EXPIRE, date.getTime());
	}

	/**
	 * 设置过期时间
	 * 
	 * @return this
	 */
	public Token setExpireTime() {
		return setExpireTime(Token.EXPIRE_TIME);
	}
	
	/**
	 * 获取过期时间
	 * @return
	 */
	public Date getExpireTime() {
		return new Date(this.getLong(EXPIRE));
	}
	
	public Token setUserKey(Long key) {
		return this.set(USER_KEY, key);
	}
	
	public Long getUserKey() {
		return this.getLong(USER_KEY);
	}
	
	public Token store() {
		return this.set(STORE, true);
	}
	
	public Boolean isStore() {
		return this.getBoolean(STORE);
	}
	
	public void create(String key, Date expireTime) {
		Token token = new Token();
		token.setKey(key);
		token.set(EXPIRE, expireTime.getTime());
	}

	@Override
	public String toString() {
		return obj.toString();
	}
	
	public boolean contains(String key) {
		return obj.containsKey(key);
	}
	
	public Object get(String key) {
		return obj.get(key);
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JSONObject getJSONObject(String key) {
		return obj.getJSONObject(key);
	}

	public JSONArray getJSONArray(String key) {
		return obj.getJSONArray(key);
	}

	public <T> T getObject(String key, Class<T> clazz) {
		return obj.getObject(key, clazz);
	}

	public Boolean getBoolean(String key) {
		return obj.getBoolean(key);
	}

	public byte[] getBytes(String key) {
		return obj.getBytes(key);
	}

	public boolean getBooleanValue(String key) {
		return obj.getBooleanValue(key);
	}

	public Byte getByte(String key) {
		return obj.getByte(key);
	}

	public byte getByteValue(String key) {
		return obj.getByteValue(key);
	}

	public Short getShort(String key) {
		return obj.getShort(key);
	}

	public short getShortValue(String key) {
		return obj.getShortValue(key);
	}

	public Integer getInteger(String key) {
		return obj.getInteger(key);
	}

	public int getIntValue(String key) {
		return obj.getIntValue(key);
	}

	public Long getLong(String key) {
		return obj.getLong(key);
	}

	public long getLongValue(String key) {
		return obj.getLongValue(key);
	}

	public Float getFloat(String key) {
		return obj.getFloat(key);
	}

	public float getFloatValue(String key) {
		return obj.getFloatValue(key);
	}

	public Double getDouble(String key) {
		return obj.getDouble(key);
	}

	public double getDoubleValue(String key) {
		return obj.getDoubleValue(key);
	}

	public BigDecimal getBigDecimal(String key) {
		return obj.getBigDecimal(key);
	}

	public BigInteger getBigInteger(String key) {
		return obj.getBigInteger(key);
	}

	public String getString(String key) {
		return obj.getString(key);
	}

	public Date getDate(String key) {
		return obj.getDate(key);
	}

	public java.sql.Timestamp getTimestamp(String key) {
		return obj.getTimestamp(key);
	}

	private static TokenManager tokenManager;

	public static TokenManager geTokenManager() {
//		if (tokenManager == null) {
//			ApplicationContext context = SpringContextUtil.getApplicationContext();
//			if (context == null) {
//				throw new RuntimeException("SPRING尚未初始化");
//			}
//
//			try {
//				tokenManager = context.getBean(TokenManager.class);
//			} catch (Exception e) {
//				logger.error("从spring中获取TokenManager失败");
//			}
//		}

		// 默认使用RedisTokenManager
		if (tokenManager == null) {
			tokenManager = new RedisTokenManager();
		}

		return tokenManager;
	}

}
