package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.aop.token.TokenManager;
import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.entity.FrSysUserExample;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrSysUserMapper;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private FrSysUserMapper sysUserMapper;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public FrSysUser selectById(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public FrSysUser selectByUserName(String username) {
        FrSysUserExample example = new FrSysUserExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<FrSysUser> frSysUsers = sysUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(frSysUsers)) {
            return null;
        }

        if (frSysUsers.size() != 1) {
            throw new CommonException(ExceptionCodes.USER_DUPLICATE);
        }
        return frSysUsers.get(0);
    }

    @Override
    public String login(LoginReq loginReq) {
        FrSysUser user = selectByUserName(loginReq.getUsername());
        if (user == null) {
            throw new CommonException(ExceptionCodes.LOGIN_USERNAME_ERROR);
        }
        if (!SecurityHelper.checkPass(loginReq.getPassword(), user.getPassword())) {
            throw new CommonException(ExceptionCodes.LOGIN_PASSWORD_ERROR);
        }

        //生成一个token，保存用户登录状态
        String token = tokenManager.createToken(user.getId().toString());
        return token;
    }
}
