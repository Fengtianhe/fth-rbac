package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.core.entity.FrSysUser;

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
}
