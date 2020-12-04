package com.fth.rbac.server.controller.vo;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/4
 * content:
 */
@Data
public class AppUpdateReq {
    @NotNull
    @NotEmpty
    private String appId;

    private String appName;

    private List<Integer> developers;
}
