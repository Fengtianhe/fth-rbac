package com.fth.rbac.server.aop.token;

/**
 * token 的model类
 * @author fengtianhe
 * @version $Id: TokenModel.java, v 0.1 2017年7月6日 上午11:15:02 fengtianhe Exp $
 */
public class TokenModel {
    private String userId;
    private String token;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
