package com.fth.rbac.server.controller.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceUpdateStatusReq implements Serializable {
    @NotNull
    @NotEmpty
    private String resourceId;
    @NotNull
    @NotEmpty
    private Integer status;

    public ResourceUpdateStatusReq() {

    }
}
