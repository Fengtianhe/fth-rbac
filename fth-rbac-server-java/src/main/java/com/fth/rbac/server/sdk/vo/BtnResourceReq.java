package com.fth.rbac.server.sdk.vo;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/14
 * content:
 */
@Data
public class BtnResourceReq {
    @NotEmpty(message = "角色信息不能为空")
    @NotNull(message = "角色信息不能为空")
    private String roleId;

    @NotEmpty(message = "路径信息不能为空")
    @NotNull(message = "路径信息不能为空")
    private String pageUrl;
}
