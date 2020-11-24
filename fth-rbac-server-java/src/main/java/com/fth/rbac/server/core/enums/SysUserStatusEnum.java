package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public class SysUserStatusEnum extends BaseEnum<Integer> {
    public SysUserStatusEnum DISABLED = new SysUserStatusEnum(SysUserStatusEnum.SYMBOL_DISABLED, "禁用");
    public SysUserStatusEnum RESET_PWD = new SysUserStatusEnum(SysUserStatusEnum.SYMBOL_RESET_PWD, "重置密码");
    public SysUserStatusEnum ENABLED = new SysUserStatusEnum(SysUserStatusEnum.SYMBOL_ENABLED, "正常");

    public static final Integer SYMBOL_DISABLED = 0;
    public static final Integer SYMBOL_RESET_PWD = 1;
    public static final Integer SYMBOL_ENABLED = 2;

    public SysUserStatusEnum(int value, String label) {
        super(value, label);
    }
}
