package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.LoginReq;
import com.fth.rbac.server.controller.vo.UserAddReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.controller.vo.UserUpdateReq;
import com.fth.rbac.server.core.entity.FrUser;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;

import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public interface UserService {
    FrUser selectById(Integer userId);

    FrUser selectByUserName(String username);

    String login(LoginReq loginReq);

    UserInfo selectInfoById(Integer userId);

    PaginationResponse<UserInfo> selectWithPagination(PaginationRequest request);

    List<FrUser> selectByIds(List<Integer> userIds);

    /**
     * 更新用户信息
     *
     * @param userId
     * @param updateReq
     * @return
     */
    void updateById(Integer userId, UserUpdateReq updateReq);

    /**
     * 通过关键词搜索
     * @param keywords
     * @return
     */
    List<UserInfo> queryByKeywords(String keywords);

    /**
     * 创建用户
     * @param userId
     * @param user
     * @return
     */
    String addUser(Integer userId, UserAddReq user);
}
