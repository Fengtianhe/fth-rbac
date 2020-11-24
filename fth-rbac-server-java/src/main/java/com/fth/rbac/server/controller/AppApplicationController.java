package com.fth.rbac.server.controller;

import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2020/11/14 2:09 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-应用")
@RestController
@RequestMapping("/api/app/application")
public class AppApplicationController {
    @Autowired
    private AppApplicationService appApplicationService;

    @ApiOperation("应用列表")
    @GetMapping("/list")
    public CommonResponse<PaginationResponse<FrAppApplication>> list(@ModelAttribute PaginationRequest request) {
        PaginationResponse<FrAppApplication> pageResponse = appApplicationService.selectWithPagination(request);
        return CommonResponse.withSuccessResp(pageResponse);
    }

    @ApiOperation("应用列表")
    @GetMapping("/all")
    public CommonResponse<List<FrAppApplication>> all() {
        List<FrAppApplication> pageResponse = appApplicationService.selectAll();
        return CommonResponse.withSuccessResp(pageResponse);
    }
}
