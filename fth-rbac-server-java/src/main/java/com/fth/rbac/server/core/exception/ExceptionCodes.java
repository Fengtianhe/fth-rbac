package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:19 下午.
 *
 * @author fengtianhe
 */
public interface ExceptionCodes {
    ExceptionCode USER_PERMISSION_DENIED = new ExceptionCode(600, "无操作权限");
    ExceptionCode AUTHORIZATION_FAILURE = new ExceptionCode(601, "鉴权失败");
    ExceptionCode LOGIN_USERNAME_ERROR = new ExceptionCode(602, "用户名输入有误");
    ExceptionCode LOGIN_PASSWORD_ERROR = new ExceptionCode(603, "密码输入有误");
    ExceptionCode LOGIN_CAPTCHA_ERROR = new ExceptionCode(604, "验证码输入错误");
    ExceptionCode USER_DUPLICATE = new ExceptionCode(605, "用戶重复");
    /**
     * 应用相关错误
     */
    ExceptionCode APPLICATION_DUPLICATE = new ExceptionCode(801, "AppID重复");
    /**
     * 资源相关错误
     */
    ExceptionCode RESOURCE_UNKNOWN_PARAMS = new ExceptionCode(701, "未知参数");
    ExceptionCode RESOURCE_SORT_NOT_CHANGE = new ExceptionCode(702, "资源未知未移动");
    ExceptionCode RESOURCE_UNKNOWN = new ExceptionCode(703, "无法找到资源");
    ExceptionCode RESOURCE_PAGE_AUTH_FAILURE = new ExceptionCode(704, "无该页面访问权限：{}");
    /**
     * 角色相关错误
     */
    ExceptionCode ROLE_SUPER_CANNOT_UPT = new ExceptionCode(801, "最大角色无法修改");
    /**
     * SDK 错误
     */
    ExceptionCode SDK_UNKNOWN_APPID = new ExceptionCode(901, "未知APPID");
    ExceptionCode SDK_UNKNOWN_APP = new ExceptionCode(902, "未知应用");
    ExceptionCode SDK_UNKNOWN_DOMAIN = new ExceptionCode(903, "未配置应用域");
    ExceptionCode SDK_ERROR_HOST = new ExceptionCode(903, "错误的请求域");
}
