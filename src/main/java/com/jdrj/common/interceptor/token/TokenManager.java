package com.jdrj.common.interceptor.token;

/**
 * 对 token 进行操作的接口
 * @author xiaobin
 *
 */
public interface TokenManager {
	/**
     * 创建一个 token 关联上指定用户
     * @return 生成的 token
     */
    public Token createToken(String secret, int timeout);
    
    /**
     * 保存token
     * @param token
     */
    public void saveToken(Token token, int timeout);

    /**
     * 检查 token 是否有效
     * @param token token
     * @return 是否有效
     */
    public boolean checkToken(Token token);

    /**
     * 从字符串中解析 token
     * @param authorization authorization
     * @return
     */
    public Token getToken(String authorization, String secret);

    public Token getToken(String key);

    /**
     * 清除 token
     * @param key key
     */
    public void deleteToken(String key);
	
}
