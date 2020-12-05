package com.fth.rbac.server.controller.vo;

import io.swagger.annotations.Api;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceTreeReq {
    @NotNull
    @NotEmpty
    private String appId;

    private Integer type;

    private Integer status;
}
