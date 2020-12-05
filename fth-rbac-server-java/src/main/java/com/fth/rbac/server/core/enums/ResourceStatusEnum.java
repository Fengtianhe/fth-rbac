package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * Created on 2020/12/1 11:40 下午.
 *
 * @author fengtianhe
 */
public class ResourceStatusEnum extends BaseEnum<Integer> {
    public ResourceStatusEnum ENABLED = new ResourceStatusEnum(ResourceStatusEnum.SYMBOL_ENABLED, "启用");
    public ResourceStatusEnum DISABLED = new ResourceStatusEnum(ResourceStatusEnum.SYMBOL_DISABLED, "停用");

    public static final Integer SYMBOL_ENABLED = 1;
    public static final Integer SYMBOL_DISABLED = 2;

    public ResourceStatusEnum(Integer key, String label) {
        super(key, label);
    }
}
