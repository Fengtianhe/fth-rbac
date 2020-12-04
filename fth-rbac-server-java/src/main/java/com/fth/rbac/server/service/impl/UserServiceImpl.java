package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.aop.token.TokenManager;
import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.controller.vo.UserAddReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.controller.vo.UserUpdateReq;
import com.fth.rbac.server.core.entity.FrUser;
import com.fth.rbac.server.core.entity.FrUserExample;
import com.fth.rbac.server.core.enums.SysUserStatusEnum;
import com.fth.rbac.server.core.enums.UserAdminEnum;
import com.fth.rbac.server.core.enums.base.EnumFactory;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrUserMapper;
import com.fth.rbac.server.core.utils.SecurityHelper;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private FrUserMapper sysUserMapper;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public FrUser selectById(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public FrUser selectByUserName(String username) {
        FrUserExample example = new FrUserExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<FrUser> FrUsers = sysUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(FrUsers)) {
            return null;
        }

        if (FrUsers.size() != 1) {
            throw new CommonException(ExceptionCodes.USER_DUPLICATE);
        }
        return FrUsers.get(0);
    }

    @Override
    public String login(LoginReq loginReq) {
        FrUser user = selectByUserName(loginReq.getUsername());
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
        FrUser user = this.selectById(userId);
        BeanUtils.copyProperties(user, info);
        return info;
    }

    @Override
    public PaginationResponse<UserInfo> selectWithPagination(PaginationRequest request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrUserExample example = new FrUserExample();
        List<FrUser> FrUsers = sysUserMapper.selectByExample(example);

        List<UserInfo> users = UserInfo.covert(FrUsers);

        return new PaginationResponse<>(users, page);
    }

    @Override
    public List<FrUser> selectByIds(List<Integer> userIds) {
        FrUserExample example = new FrUserExample();
        example.createCriteria().andIdIn(userIds);
        return sysUserMapper.selectByExample(example);
    }

    @Override
    public void updateById(Integer userId, UserUpdateReq updateReq) {
        FrUser updateData = new FrUser();
        if (!StringUtils.isEmpty(updateReq.getNickname())) {
            updateData.setNickname(updateReq.getNickname());
        }
        if (!StringUtils.isEmpty(updateReq.getPhone())) {
            updateData.setPhone(updateReq.getPhone());
        }
        updateData.setId(userId);
        sysUserMapper.updateByPrimaryKeySelective(updateData);
    }

    @Override
    public List<UserInfo> queryByKeywords(String keywords) {
        FrUserExample example = new FrUserExample();
        FrUserExample.Criteria c1 = example.createCriteria();
        c1.andNicknameLike("%" + keywords + "%");
        FrUserExample.Criteria c2 = example.createCriteria();
        c2.andUsernameLike("%" + keywords + "%");
        example.or(c2);

        List<FrUser> FrUsers = sysUserMapper.selectByExample(example);
        return UserInfo.covert(FrUsers);
    }

    @Override
    public String addUser(Integer userId, UserAddReq user) {
        FrUser currUser = this.selectById(userId);
        if (!UserAdminEnum.SYMBOL_Y.equals(currUser.getAdmin())) {
            throw new CommonException(ExceptionCodes.USER_PERMISSION_DENIED);
        }

        String username = user.getUsername().toLowerCase();
        FrUser frUser = this.selectByUserName(username);
        if (frUser != null) {
            throw new CommonException(ExceptionCodes.USER_DUPLICATE);
        }

        // 前端传给后端的密码是md5加密的
        String password = SecurityHelper.genPass();
        String security = SecurityHelper.securityPass(SecurityHelper.md5(password));

        FrUser saveData = new FrUser();
        saveData.setCreatedAt(new Date());
        saveData.setStatus(SysUserStatusEnum.SYMBOL_RESET_PWD);
        saveData.setUsername(username);
        saveData.setPassword(security);
        sysUserMapper.insertSelective(saveData);

        return password;
    }

    public static void main(String[] args) {

    }
}
