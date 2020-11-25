package com.fth.rbac.server.core.exception;

/**
 * Created on 2020/11/14 1:15 下午.
 *
 * @author fengtianhe
 */
public class CommonException extends RuntimeException {
    private int code;

    private String msg;

    public CommonException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonException(ExceptionCode exceptionCode) {
        super(exceptionCode.msg());
        this.code = exceptionCode.code();
        this.msg = exceptionCode.msg();
    }

    public CommonException args(String... args) {
        if (this.msg != null && !"".equals(this.msg) && args != null && args.length > 0) {
            this.msg = this.msg.replaceAll("\\{\\}", "%s");
            this.msg = String.format(this.msg, args);
        }
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
