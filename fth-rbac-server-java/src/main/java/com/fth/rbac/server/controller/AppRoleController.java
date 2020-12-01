package com.fth.rbac.server.controller;

import com.fth.rbac.server.core.utils.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2020/12/1 8:22 下午.
 *
 * @author fengtianhe
 */
@Api(tags = "应用管理-角色")
@RestController
@RequestMapping("/api/app/role")
public class AppRoleController extends BaseController {
}
