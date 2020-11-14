package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:19 下午.
 *
 * @author fengtianhe
 */
public interface ExceptionCodes {
    ExceptionCode AUTHORIZATION_FAILURE = new ExceptionCode(401, "鉴权失败");
}
