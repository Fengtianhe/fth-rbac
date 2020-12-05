package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.RoleAssignReq;
import com.fth.rbac.server.controller.vo.RoleListReq;
import com.fth.rbac.server.core.entity.FrRole;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleList;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2020/12/1 8:22 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-角色")
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表")
    @GetMapping("/list")
    public CommonResponse<PaginationResponse<FrRole>> list(@ModelAttribute RoleListReq request) {
        validateData(request);
        PaginationResponse<FrRole> rolePaginationResponse = roleService.selectWithPagination(request);
        return CommonResponse.withSuccessResp(rolePaginationResponse);
    }

    @ApiOperation("分配资源")
    @PostMapping("/assign")
    public CommonResponse<Boolean> list(@RequestBody RoleAssignReq roleAssignReq, HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);
        validateData(request);
        roleService.assignResource(roleAssignReq, userId);
        return CommonResponse.withSuccessResp(true);
    }

    @ApiOperation("获取已分配的资源")
    @GetMapping("/assign-resourceids")
    public CommonResponse<List<String>> list(@RequestParam String roleId) {
        List<String> resourceIds = roleService.selectAssignResourceIds(roleId);
        return CommonResponse.withSuccessResp(resourceIds);
    }

    @ApiOperation("获取角色详情")
    @GetMapping("/role/{roleId}")
    public CommonResponse<FrRole> detail(@PathVariable String roleId) {
        FrRole frRole = roleService.selectByRoleId(roleId);
        return CommonResponse.withSuccessResp(frRole);
    }
}
