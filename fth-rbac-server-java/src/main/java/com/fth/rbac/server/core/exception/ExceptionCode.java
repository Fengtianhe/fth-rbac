package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:17 下午.
 *
 * @author fengtianhe
 */
public class ExceptionCode {
    private int code;
    private String msg;

    public ExceptionCode(int code) {
        this(code, (String)null);
    }

    public ExceptionCode(int code, String msg, String... args) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
