package com.jdrj.common.interceptor.token;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

	private static final String SECRET = "secret";
	private static final String TOKEN = "token";
	private static final String ISS = "wd";
	private static final Integer TIMEOUT = 60 * 60 * 24 * 30; //60分钟 * 24小时 * 30天

	public static String getToken(String token, String secret) {
		DecodedJWT jwt = verifier(token, secret);

		if (jwt != null) {
			return getToken(jwt);
		}
		return null;
	}
	
	public static String getToken(DecodedJWT jwt) {
		Claim claim = jwt.getClaims().get(TOKEN);
		if (null == claim) {
			return null;
		}
		return claim.asString();
	}
	
	/**
	 * 验证jwt 如果为空 验证失败
	 * 
	 * @param token
	 * @return
	 */
	public static DecodedJWT verifier(String token, String secret) {
		try {
			
			if (StringUtils.isEmpty(secret)) {
				secret = SECRET;
			}
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISS).build();
			DecodedJWT jwt = verifier.verify(token);

			return jwt;
		} catch (JWTVerificationException exception) {
			// Invalid signature/claims
			exception.printStackTrace();
			
		}
		return null;
	}
	
	/**
	 * 判断jwt是否过期
	 * @param token
	 * @return
	 */
	public static Boolean expires(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TokenConfig.SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISS).build();
			verifier.verify(token);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	/**
	 * 创建jwt
	 * @param id jwt id
	 * @param second 过期时间
	 * @param secret 秘钥
	 * @return
	 */
	public static String create(String id, Integer second, String secret) {
		return create(id, null, second, secret);
	}
	
	/**
	 * 创建jwt
	 * @param id jwt id
	 * @param expires 过期时间
	 * @param secret 秘钥
	 * @return
	 */
	public static String create(String id, Date expires, String secret) {
		return create(id, null, expires, secret);
	}
	
	/**
	 * 创建jwt
	 * @param id jwt id
	 * @param token token
	 * @param second 过期时间
	 * @param secret 秘钥
	 * @return
	 */
	public static String create(String id, String token, Integer second, String secret) {
		
		Date current = new Date();
		if (null == second || second < 0) {
			second = TIMEOUT;
		}
		
		Date expires = DateUtil.offset(current, DateField.SECOND, second);
		return create(id, token, expires, secret);
	}
	
	/**
	 * 创建jwt
	 * @param id jwt id
	 * @param token token
	 * @param expires 过期时间
	 * @param secret 秘钥
	 * @return
	 */
	public static String create(String id, String token, Date expires, String secret) {
		return create(id, token, expires, secret, null);
	}
	
	/**
	 * 创建jwt
	 * @param id jwt id
	 * @param token token
	 * @param expires 过期时间
	 * @param secret 秘钥
	 * @return
	 */
	public static String create(String id, String token, Date expires, String secret, Map<String, Object> headerClaims) {
		try {
			
			if (StringUtils.isEmpty(secret)) {
				secret = SECRET;
			}
			
			Algorithm algorithm = Algorithm.HMAC256(secret);

			Builder builder = JWT.create()
					.withJWTId(id)
					.withIssuer(ISS)
					.withIssuedAt(new Date());
			
			if (StringUtils.isNotEmpty(token)) {
				builder.withClaim(TOKEN, token);
			}
			
			if (null != headerClaims && !headerClaims.isEmpty()) {
				builder.withHeader(headerClaims);
			}
			
			builder.withExpiresAt(expires);
			
			String jwt = builder.sign(algorithm);
			return jwt;
		} catch (JWTCreationException exception) {
			exception.printStackTrace();
		}
		return "";
	}

	

}
