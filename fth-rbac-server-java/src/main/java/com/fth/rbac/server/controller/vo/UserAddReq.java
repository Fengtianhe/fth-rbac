package com.fth.rbac.server.controller.vo;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/4
 * content:
 */
@Data
public class UserAddReq {
    @NotEmpty
    @NotNull
    private String username;
}
