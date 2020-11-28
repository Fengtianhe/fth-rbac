package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.aop.token.TokenManager;
import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.entity.FrSysUserExample;
import com.fth.rbac.server.core.enums.SysUserStatusEnum;
import com.fth.rbac.server.core.enums.base.EnumFactory;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrSysUserMapper;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public UserInfo selectInfoById(Integer userId) {
        UserInfo info = new UserInfo();
        FrSysUser user = this.selectById(userId);
        BeanUtils.copyProperties(user, info);
        return info;
    }

    @Override
    public PaginationResponse<UserInfo> selectWithPagination(PaginationRequest request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrSysUserExample example = new FrSysUserExample();
        List<FrSysUser> frSysUsers = sysUserMapper.selectByExample(example);

        List<UserInfo> users = new ArrayList<>();
        frSysUsers.forEach(u -> {
            UserInfo user = new UserInfo();
            BeanUtils.copyProperties(u, user);
            user.setStatus(EnumFactory.getByValue(SysUserStatusEnum.class, u.getStatus().intValue()).getLabel());
            users.add(user);
        });

        return new PaginationResponse<>(users, page);
    }

    @Override
    public List<FrSysUser> selectByIds(List<Integer> userIds) {
        FrSysUserExample example = new FrSysUserExample();
        example.createCriteria().andIdIn(userIds);
        return sysUserMapper.selectByExample(example);
    }
}
