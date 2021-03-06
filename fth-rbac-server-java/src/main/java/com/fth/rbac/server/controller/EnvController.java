package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.EnvVo;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.service.EnvService;
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
@RequestMapping("/api/env")
public class EnvController extends BaseController {
    @Autowired
    private EnvService envService;

    @ApiOperation("通过应用查询")
    @GetMapping("/by-appid/{appId}")
    public CommonResponse<List<EnvVo>> all(@PathVariable String appId) {
        List<EnvVo> response = envService.selectByAppId(appId);
        return CommonResponse.withSuccessResp(response);
    }
}
