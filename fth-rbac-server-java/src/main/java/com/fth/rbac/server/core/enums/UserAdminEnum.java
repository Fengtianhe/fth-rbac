package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/4
 * content:
 */
public class UserAdminEnum extends BaseEnum {
    public UserAdminEnum Y = new UserAdminEnum(UserAdminEnum.SYMBOL_Y, "是");
    public UserAdminEnum N = new UserAdminEnum(UserAdminEnum.SYMBOL_N, "否");

    public static final Integer SYMBOL_Y = 1;
    public static final Integer SYMBOL_N = 0;

    public UserAdminEnum(Integer key, String label) {
        super(key, label);
    }
}
