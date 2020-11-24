package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:19 下午.
 *
 * @author fengtianhe
 */
public interface ExceptionCodes {
    ExceptionCode AUTHORIZATION_FAILURE = new ExceptionCode(401, "鉴权失败");
    ExceptionCode LOGIN_USERNAME_ERROR = new ExceptionCode(402, "用户名输入有误");
    ExceptionCode LOGIN_PASSWORD_ERROR = new ExceptionCode(403, "密码输入有误");
    ExceptionCode USER_DUPLICATE = new ExceptionCode(404, "用戶重复");
}
