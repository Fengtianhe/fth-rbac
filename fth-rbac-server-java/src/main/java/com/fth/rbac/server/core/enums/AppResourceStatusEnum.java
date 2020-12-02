package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * Created on 2020/12/1 11:40 下午.
 *
 * @author fengtianhe
 */
public class AppResourceStatusEnum extends BaseEnum<Integer> {
    public AppResourceStatusEnum ENABLED = new AppResourceStatusEnum(AppResourceStatusEnum.SYMBOL_ENABLED, "启用");
    public AppResourceStatusEnum DISABLED = new AppResourceStatusEnum(AppResourceStatusEnum.SYMBOL_DISABLED, "停用");

    public static final Integer SYMBOL_ENABLED = 1;
    public static final Integer SYMBOL_DISABLED = 2;

    public AppResourceStatusEnum(Integer key, String label) {
        super(key, label);
    }
}
