package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
@Api(tags = "系统管理-用户")
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public CommonResponse login(@RequestBody LoginReq loginReq) {
        String res = sysUserService.login(loginReq);
        return CommonResponse.withSuccessResp(res);
    }
}
