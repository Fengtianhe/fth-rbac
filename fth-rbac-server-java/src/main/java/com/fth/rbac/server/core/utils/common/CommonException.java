package com.fth.rbac.server.core.utils.common;

/**
 * @author 冯天鹤
 * comment: 公共异常类
 */
public class CommonException extends RuntimeException {
    private int code;
    private String message;

    public CommonException(ExceptionCode code) {
        this.code = code.code();
        this.message = code.msg();
    }

    public CommonException(ExceptionCode code, Throwable e) {
        this(code.code(), code.msg(), e);
    }

    public CommonException(ExceptionCode code, String msg, String... objects) {
        this(code);
        if (msg != null && !"".equals(msg) && objects != null && objects.length > 0) {
            String message = msg.replaceAll("\\{\\}", "%s");
            this.message = String.format(message, objects);
        }
    }

    CommonException(int code, String msg, Throwable e) {
        super(e);
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "-" + this.code;
    }
}

