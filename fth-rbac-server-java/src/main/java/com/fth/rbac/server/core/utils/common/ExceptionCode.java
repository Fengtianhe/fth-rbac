package com.fth.rbac.server.core.utils.common;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/23
 * content:
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
        this.args(args);
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public ExceptionCode args(String... args) {
        if (this.msg != null && !"".equals(this.msg) && args != null && args.length > 0) {
            this.msg = this.msg.replaceAll("\\{\\}", "%s");
            this.msg = String.format(this.msg, args);
        }

        return this;
    }
}