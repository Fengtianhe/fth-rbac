package com.fth.rbac.server.controller.vo;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceUpdateMenuReq implements Serializable {
    @NotNull
    @NotEmpty
    private String resourceId;
    @NotNull
    @NotEmpty
    private Integer inMenu;

    public ResourceUpdateMenuReq() {

    }
}
