package com.fth.rbac.server.core.enums;

import com.fth.rbac.server.core.enums.base.BaseEnum;

/**
 * Created on 2020/12/1 11:40 下午.
 *
 * @author fengtianhe
 */
public class AppResourceTypeEnum extends BaseEnum<Integer> {
    public AppResourceTypeEnum PAGE = new AppResourceTypeEnum(AppResourceTypeEnum.SYMBOL_PAGE, "页面");
    public AppResourceTypeEnum BTN = new AppResourceTypeEnum(AppResourceTypeEnum.SYMBOL_BTN, "按钮");

    public static final Integer SYMBOL_PAGE = 1;
    public static final Integer SYMBOL_BTN = 2;

    public AppResourceTypeEnum(Integer key, String label) {
        super(key, label);
    }
}
