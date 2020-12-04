package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.AppUpdateReq;
import com.fth.rbac.server.controller.vo.AppVo;
import com.fth.rbac.server.controller.vo.AppSaveReq;
import com.fth.rbac.server.controller.vo.UserInfo;
import com.fth.rbac.server.core.entity.*;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrAppDeveloperMapper;
import com.fth.rbac.server.core.mapper.FrAppMapper;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppService;
import com.fth.rbac.server.service.EnvService;
import com.fth.rbac.server.service.RoleService;
import com.fth.rbac.server.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2020/11/14 2:17 下午.
 *
 * @author fengtianhe
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private FrAppMapper FrAppMapper;
    @Autowired
    private FrAppDeveloperMapper appDeveloperMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EnvService envService;
    @Autowired
    private RoleService roleService;

    @Override
    public PaginationResponse<AppVo> selectWithPagination(PaginationRequest request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrAppExample example = new FrAppExample();
        List<FrApp> FrApps = FrAppMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(FrApps)) {
            return new PaginationResponse<>(Collections.EMPTY_LIST, page);
        }

        List<Integer> userIds = FrApps.stream().map(FrApp::getCreator).collect(Collectors.toList());
        List<FrUser> users = userService.selectByIds(userIds);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(FrUser::getId, FrUser::getUsername));

        List<AppVo> responseList = new ArrayList<>();
        FrApps.forEach(app -> {
            AppVo vo = new AppVo();
            BeanUtils.copyProperties(app, vo);
            vo.setCreatorName(userMap.get(app.getCreator()));
            // 查询开发人员
            List<UserInfo> developers = this.getDevelopers(app.getAppId());
            vo.setDevelopers(developers);
            responseList.add(vo);
        });

        return new PaginationResponse<>(responseList, page);
    }

    @Override
    public List<FrApp> selectAll(Integer userId) {
        FrAppExample example = new FrAppExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<FrApp> FrApps = FrAppMapper.selectByExample(example);
        return FrApps;
    }

    @Override
    public void add(AppSaveReq applicationReq, Integer userId) {
        FrApp saveData = new FrApp();
        String appId = applicationReq.getAppId().toLowerCase().replace('_', '-');
        FrApp application = this.selectByAppId(appId);
        if (application != null) {
            throw new CommonException(ExceptionCodes.APPLICATION_DUPLICATE);
        }
        saveData.setAppId(appId);
        saveData.setAppName(applicationReq.getAppName());
        saveData.setCreator(userId);
        saveData.setCreatedAt(new Date());

        FrAppMapper.insertSelective(saveData);

        // 在创建应用的时候默认会创建三个环境dev,stg,prd
        envService.createDefaultEnv(appId);
        // 创建一个默认角色
        roleService.createDefaultRole(appId);
    }

    @Override
    public FrApp selectByAppId(String appId) {
        FrAppExample example = new FrAppExample();
        List<FrApp> FrApps = FrAppMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(FrApps)) {
            return null;
        }
        return FrApps.get(0);
    }

    @Override
    public void update(AppUpdateReq applicationReq, Integer creator) {
        this.deleteDevelopers(applicationReq.getAppId());
        this.addDevelopers(applicationReq.getAppId(), applicationReq.getDevelopers(), creator);
    }

    /**
     * 查询开发人员
     *
     * @param appId
     * @return
     */
    private List<UserInfo> getDevelopers(String appId) {
        FrAppDeveloperExample example = new FrAppDeveloperExample();
        example.createCriteria().andAppIdEqualTo(appId);
        List<FrAppDeveloper> appDevelopers = appDeveloperMapper.selectByExample(example);

        List<UserInfo> developers = new ArrayList<>();
        appDevelopers.forEach(d->{
            developers.add(userService.selectInfoById(d.getUserId()));
        });
        return developers;
    }

    /**
     * 删除开发人员
     *
     * @param appId
     */
    private void deleteDevelopers(String appId) {
        FrAppDeveloperExample example = new FrAppDeveloperExample();
        example.createCriteria().andAppIdEqualTo(appId);
        appDeveloperMapper.deleteByExample(example);
    }

    /**
     * 添加开发人员
     *
     * @param appId
     * @param userIds
     * @param creator
     */
    private void addDevelopers(String appId, List<Integer> userIds, Integer creator) {
        FrAppDeveloper saveData;
        for (Integer userId : userIds) {
            saveData = new FrAppDeveloper();
            saveData.setUserId(userId);
            saveData.setCreatedAt(new Date());
            saveData.setAppId(appId);
            saveData.setCreator(creator);
            appDeveloperMapper.insertSelective(saveData);
        }
    }
}
