package com.fth.rbac.server.aop.token;

import java.util.Map;

/**
 * 
 * @author fengtianhe
 * @version $Id: TokenManager.java, v 0.1 2017年7月6日 上午11:19:09 fengtianhe Exp $
 */
public interface TokenManager {
    /**
     * 创建一个 token 关联上指定用户
     * @param userId 指定用户的 id
     * @return 生成的 token
     */
    public String createToken(String userId);

    /**
     * 检查 token 是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析 token
     * @param authentication 加密后的字符串
     * @return
     */
    public TokenModel getToken(String authentication);

    /**
     * 清除 token
     * @param userId 登录用户的 id
     */
    public void deleteToken(String userId);

    /**
     *  设置session内容
     *  
     *  如果已经存在，会覆盖原有的内容
     *  
     *  注：忽略空值，如果不忽略redis会设置失败
     * 
     * @param userId
     * @param infos
     */
    public void setSession(String userId, Map<String, String> infos);

    /**
     *  获取session内容
     * @return 
     *  
     */
    public Map<Object, Object> getSession(String userId);

    /**
     * 
     * 
     * @param userId
     * @param fields
     * @return
     */
    public boolean delFromSession(String userId, Object... fields);
}
