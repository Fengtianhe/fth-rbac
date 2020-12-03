package com.fth.rbac.server.controller;

import com.fth.rbac.server.controller.vo.AppVo;
import com.fth.rbac.server.controller.vo.SaveApplicationReq;
import com.fth.rbac.server.core.entity.FrApp;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.BaseController;
import com.fth.rbac.server.core.utils.common.CommonResponse;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2020/11/14 2:09 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-应用")
@RestController
@RequestMapping("/api/app")
public class AppController extends BaseController {
    @Autowired
    private AppService appAppService;

    @ApiOperation("应用列表")
    @GetMapping("/list")
    public CommonResponse<PaginationResponse<AppVo>> list(@ModelAttribute PaginationRequest request) {
        PaginationResponse<AppVo> pageResponse = appAppService.selectWithPagination(request);
        return CommonResponse.withSuccessResp(pageResponse);
    }

    @ApiOperation("应用列表")
    @GetMapping("/all")
    public CommonResponse<List<FrApp>> all(HttpServletRequest request) {
        Integer userId = SecurityHelper.userId(request);
        List<FrApp> pageResponse = appAppService.selectAll(userId);
        return CommonResponse.withSuccessResp(pageResponse);
    }

    @ApiOperation("新增应用")
    @PostMapping("")
    public CommonResponse<Boolean> add(@RequestBody SaveApplicationReq applicationReq, HttpServletRequest httpServletRequest) {
        validateData(applicationReq);
        Integer userId = SecurityHelper.userId(httpServletRequest);
        appAppService.add(applicationReq, userId);
        return CommonResponse.withSuccessResp(true);
    }
}
