package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:19 下午.
 *
 * @author fengtianhe
 */
public interface ExceptionCodes {
    ExceptionCode AUTHORIZATION_FAILURE = new ExceptionCode(601, "鉴权失败");
    ExceptionCode LOGIN_USERNAME_ERROR = new ExceptionCode(602, "用户名输入有误");
    ExceptionCode LOGIN_PASSWORD_ERROR = new ExceptionCode(603, "密码输入有误");
    ExceptionCode LOGIN_CAPTCHA_ERROR = new ExceptionCode(604, "验证码输入错误");
    ExceptionCode USER_DUPLICATE = new ExceptionCode(605, "用戶重复");
}
