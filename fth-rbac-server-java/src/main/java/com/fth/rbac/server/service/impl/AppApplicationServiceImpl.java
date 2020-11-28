package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.FrAppApplicationVo;
import com.fth.rbac.server.controller.vo.SaveApplicationReq;
import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.entity.FrAppApplicationExample;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCodes;
import com.fth.rbac.server.core.mapper.FrAppApplicationMapper;
import com.fth.rbac.server.core.utils.common.PaginationRequest;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppApplicationService;
import com.fth.rbac.server.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class AppApplicationServiceImpl implements AppApplicationService {
    @Autowired
    private FrAppApplicationMapper frAppApplicationMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PaginationResponse<FrAppApplicationVo> selectWithPagination(PaginationRequest request) {
        Page page = PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        FrAppApplicationExample example = new FrAppApplicationExample();
        List<FrAppApplication> frAppApplications = frAppApplicationMapper.selectByExample(example);

        List<Integer> userIds = frAppApplications.stream().map(FrAppApplication::getCreator).collect(Collectors.toList());
        List<FrSysUser> users = sysUserService.selectByIds(userIds);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(FrSysUser::getId, FrSysUser::getUsername));

        List<FrAppApplicationVo> responseList = new ArrayList<>();
        frAppApplications.forEach(app -> {
            FrAppApplicationVo vo = new FrAppApplicationVo();
            BeanUtils.copyProperties(app, vo);
            vo.setCreatorName(userMap.get(app.getCreator()));
            responseList.add(vo);
        });

        return new PaginationResponse<>(responseList, page);
    }

    @Override
    public List<FrAppApplication> selectAll() {
        return new ArrayList<>();
    }

    @Override
    public void add(SaveApplicationReq applicationReq, Integer userId) {
        FrAppApplication saveData = new FrAppApplication();
        String appId = applicationReq.getAppId().toLowerCase();
        FrAppApplication application = this.selectByAppId(appId);
        if (application != null) {
            throw new CommonException(ExceptionCodes.APPLICATION_DUPLICATE);
        }
        saveData.setAppId(appId);
        saveData.setAppName(applicationReq.getAppName());
        saveData.setCreator(userId);
        saveData.setCreatedAt(new Date());

        frAppApplicationMapper.insertSelective(saveData);
    }

    @Override
    public FrAppApplication selectByAppId(String appId) {
        FrAppApplicationExample example = new FrAppApplicationExample();
        List<FrAppApplication> frAppApplications = frAppApplicationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(frAppApplications)) {
            return null;
        }
        return frAppApplications.get(0);
    }
}
