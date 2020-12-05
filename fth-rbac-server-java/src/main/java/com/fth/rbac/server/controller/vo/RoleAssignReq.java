package com.fth.rbac.server.controller.vo;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * Created on 2020/12/5 11:50 下午.
 *
 * @author fengtianhe
 */
@Data
public class RoleAssignReq {
    @NotEmpty
    @NotNull
    private String appId;

    private String roleId;
    @NotNull
    @NotEmpty
    private String roleName;
    @NotNull
    @NotEmpty
    private List<String> resourceIds;
}
