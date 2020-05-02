package com.jdrj.common.interceptor.token;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 将token数据绑定到本地线程上
 * 
 * @author xiaobin
 *
 */
public class TokenUtils {
	public static ThreadLocal<Token> local = new ThreadLocal<Token>();
	
	public static void set(Token token) {
		local.set(token);
	}
	
	public static Token get() {
		return local.get();
	}

	private static Token token() {
		Token token = local.get();
		if (!token.contains(Token.REMOTE)) {
			token = Token.geTokenManager().getToken(token.getKey());
			if (null == token) {
				return local.get();
			}
			token.set(Token.REMOTE, 1);
		}
		local.set(token);
		return token;
	}
	
	public static Token getAndRemove() {
		Token token = local.get();
		token.remove(Token.REMOTE);
		token.remove(Token.STORE);
		return token;
	}

	public static Token add(String key, Object value) {
		Token token = token();
		token.set(key, value);
		token.store();
		return token;
	}
	
	public static Token remove(String key) {
		Token token = token();
		token.remove(key);
		token.store();
		return token;
	}
	
	public static JSONObject getJSONObject(String key) {
		return token().getJSONObject(key);
	}

	public static JSONArray getJSONArray(String key) {
		return token().getJSONArray(key);
	}

	public static <T> T getObject(String key, Class<T> clazz) {
		return token().getObject(key, clazz);
	}

	public static Boolean getBoolean(String key) {
		return token().getBoolean(key);
	}

	public static byte[] getBytes(String key) {
		return token().getBytes(key);
	}

	public static boolean getBooleanValue(String key) {
		return token().getBooleanValue(key);
	}

	public static Byte getByte(String key) {
		return token().getByte(key);
	}

	public static byte getByteValue(String key) {
		return token().getByteValue(key);
	}

	public static Short getShort(String key) {
		return token().getShort(key);
	}

	public static short getShortValue(String key) {
		return token().getShortValue(key);
	}

	public static Integer getInteger(String key) {
		return token().getInteger(key);
	}

	public static int getIntValue(String key) {
		return token().getIntValue(key);
	}

	public static Long getLong(String key) {
		return token().getLong(key);
	}

	public static long getLongValue(String key) {
		return token().getLongValue(key);
	}

	public static Float getFloat(String key) {
		return token().getFloat(key);
	}

	public static float getFloatValue(String key) {
		return token().getFloatValue(key);
	}

	public static Double getDouble(String key) {
		return token().getDouble(key);
	}

	public static double getDoubleValue(String key) {
		return token().getDoubleValue(key);
	}

	public static BigDecimal getBigDecimal(String key) {
		return token().getBigDecimal(key);
	}

	public static BigInteger getBigInteger(String key) {
		return token().getBigInteger(key);
	}

	public static String getString(String key) {
		return token().getString(key);
	}

	public static Date getDate(String key) {
		return token().getDate(key);
	}

	public static java.sql.Timestamp getTimestamp(String key) {
		return token().getTimestamp(key);
	}
}
