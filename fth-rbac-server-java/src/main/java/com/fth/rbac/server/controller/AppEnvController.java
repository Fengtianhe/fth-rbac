package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.FrAppEnvVo;
import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.entity.FrAppEnv;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.service.AppEnvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2020/11/30 8:30 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-环境")
@RestController
@RequestMapping("/api/app/env")
public class AppEnvController extends BaseController {
    @Autowired
    private AppEnvService envService;

    @ApiOperation("通过应用查询")
    @GetMapping("/by-appid/{appId}")
    public CommonResponse<List<FrAppEnvVo>> all(@PathVariable String appId) {
        List<FrAppEnvVo> response = envService.selectByAppId(appId);
        return CommonResponse.withSuccessResp(response);
    }
}
