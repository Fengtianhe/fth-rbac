package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * Created on 2020/12/1 11:40 下午.
 *
 * @author fengtianhe
 */
public class AppResourceInmenuEnum extends BaseEnum<Integer> {
    public AppResourceInmenuEnum SHOW = new AppResourceInmenuEnum(AppResourceInmenuEnum.SYMBOL_SHOW, "展示");
    public AppResourceInmenuEnum HIDE = new AppResourceInmenuEnum(AppResourceInmenuEnum.SYMBOL_HIDE, "隐藏");

    public static final Integer SYMBOL_SHOW = 1;
    public static final Integer SYMBOL_HIDE = 2;

    public AppResourceInmenuEnum(Integer key, String label) {
        super(key, label);
    }
}
