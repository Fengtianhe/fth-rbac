package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public interface SysUserService {
    FrSysUser selectById(Integer userId);

    FrSysUser selectByUserName(String username);

    String login(LoginReq loginReq);

    UserInfo selectInfoById(Integer userId);

    PaginationResponse<UserInfo> selectWithPagination(PaginationRequest request);
}
