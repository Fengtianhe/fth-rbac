package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * Created on 2020/12/1 11:40 下午.
 *
 * @author fengtianhe
 */
public class ResourceInmenuEnum extends BaseEnum<Integer> {
    public ResourceInmenuEnum SHOW = new ResourceInmenuEnum(ResourceInmenuEnum.SYMBOL_SHOW, "展示");
    public ResourceInmenuEnum HIDE = new ResourceInmenuEnum(ResourceInmenuEnum.SYMBOL_HIDE, "隐藏");

    public static final Integer SYMBOL_SHOW = 1;
    public static final Integer SYMBOL_HIDE = 2;

    public ResourceInmenuEnum(Integer key, String label) {
        super(key, label);
    }
}
