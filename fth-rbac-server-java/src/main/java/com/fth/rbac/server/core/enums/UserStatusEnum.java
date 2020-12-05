package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public class UserStatusEnum extends BaseEnum<Integer> {
    public UserStatusEnum DISABLED = new UserStatusEnum(UserStatusEnum.SYMBOL_DISABLED, "禁用");
    public UserStatusEnum RESET_PWD = new UserStatusEnum(UserStatusEnum.SYMBOL_RESET_PWD, "重置密码");
    public UserStatusEnum ENABLED = new UserStatusEnum(UserStatusEnum.SYMBOL_ENABLED, "正常");

    public static final Integer SYMBOL_DISABLED = 0;
    public static final Integer SYMBOL_RESET_PWD = 1;
    public static final Integer SYMBOL_ENABLED = 2;

    public UserStatusEnum(int value, String label) {
        super(value, label);
    }
}
