package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.utils.common.PaginationRequest;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created on 2020/12/5 2:06 下午.
 *
 * @author fengtianhe
 */
@Data
public class RoleListReq extends PaginationRequest {
    @NotEmpty
    @NotNull
    private String appId;
}
