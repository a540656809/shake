package com.jdrj.common.interceptor.token;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AESUtil {

	/**
	 * AES 加密操作
	 * @param content  待加密内容
	 * @param password 加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String password) {
		AES aes = getAes(password);
		// 加密为16进制表示
		String encryptHex = aes.encryptHex(content);
		return encryptHex;
	}

	/**
	 * AES 解密操作
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password) {
		AES aes = getAes(password);
		// 解密为字符串
		String decryptStr = aes.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
		return decryptStr;
	}

	private static AES getAes(String password) {
		byte[] b = password.getBytes();
		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), b).getEncoded();
		// 构建
		AES aes = SecureUtil.aes(key);
		return aes;
	}

}
