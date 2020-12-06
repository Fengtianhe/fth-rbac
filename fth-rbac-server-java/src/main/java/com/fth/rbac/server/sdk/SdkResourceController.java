package com.fth.rbac.server.sdk;

import com.fth.rbac.server.controller.vo.ResourceTreeResp;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.sdk.vo.MenuTreeResp;
import com.fth.rbac.server.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2020/12/6 1:53 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "SDK")
@RestController
@RequestMapping("/sdk/resource")
public class SdkResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("获取目录")
    @GetMapping("/menu")
    public CommonResponse<List<MenuTreeResp>> menu(@RequestParam String roleId) {
        String[] roleIds = roleId.split(",");
        List<MenuTreeResp> menu = resourceService.getMenuByRoleId(Arrays.asList(roleIds));
        return CommonResponse.withSuccessResp(menu);
    }
}
