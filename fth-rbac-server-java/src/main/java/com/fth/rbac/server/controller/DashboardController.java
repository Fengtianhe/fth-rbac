package com.fth.rbac.server.controller;

import com.fth.rbac.server.core.entity.FrAppExample;
import com.fth.rbac.server.core.entity.FrResourceExample;
import com.fth.rbac.server.core.entity.FrRoleExample;
import com.fth.rbac.server.core.mapper.FrAppMapper;
import com.fth.rbac.server.core.mapper.FrResourceMapper;
import com.fth.rbac.server.core.mapper.FrRoleMapper;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/15
 * content:
 */
@Api(tags = "首页")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {
    @Autowired
    FrAppMapper appMapper;
    @Autowired
    FrResourceMapper resourceMapper;
    @Autowired
    FrRoleMapper roleMapper;

    @GetMapping("/overview")
    public CommonResponse<Map<String, Object>> overview() {
        Map<String, Object> response = new HashMap<>();
        response.put("numApp", this.countApp());
        response.put("numRol", this.countRole());
        response.put("numRes", this.countResource());

        return CommonResponse.withSuccessResp(response);
    }

    private long countApp() {
        return appMapper.countByExample(new FrAppExample());
    }

    private long countRole() {
        return roleMapper.countByExample(new FrRoleExample());
    }

    private long countResource() {
        return resourceMapper.countByExample(new FrResourceExample());
    }
}
