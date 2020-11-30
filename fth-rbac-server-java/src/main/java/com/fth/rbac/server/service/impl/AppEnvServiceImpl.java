package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.FrAppApplicationVo;
import com.fth.rbac.server.controller.vo.FrAppEnvVo;
import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.entity.FrAppEnv;
import com.fth.rbac.server.core.entity.FrAppEnvExample;
import com.fth.rbac.server.core.entity.FrSysUser;
import com.fth.rbac.server.core.mapper.FrAppEnvMapper;
import com.fth.rbac.server.core.utils.common.PaginationResponse;
import com.fth.rbac.server.service.AppEnvService;
import com.fth.rbac.server.service.SysUserService;
import com.sun.deploy.ui.AppInfo;
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
 * Created on 2020/11/30 7:59 下午.
 *
 * @author fengtianhe
 */
@Service
public class AppEnvServiceImpl implements AppEnvService {
    @Autowired
    private FrAppEnvMapper appEnvMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public void createDefaultEnv(String appId) {
        this.addEnv(appId, "dev", "开发环境", 0);
        this.addEnv(appId, "stg", "测试环境", 0);
        this.addEnv(appId, "prd", "生产环境", 0);
    }

    @Override
    public List<FrAppEnvVo> selectByAppId(String appId) {
        FrAppEnvExample envExample = new FrAppEnvExample();
        envExample.createCriteria().andAppIdEqualTo(appId);
        List<FrAppEnv> frAppEnvs = appEnvMapper.selectByExample(envExample);

        if (CollectionUtils.isEmpty(frAppEnvs)) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> userIds = frAppEnvs.stream().map(FrAppEnv::getCreator).collect(Collectors.toList());
        List<FrSysUser> users = sysUserService.selectByIds(userIds);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(FrSysUser::getId, FrSysUser::getUsername));

        List<FrAppEnvVo> responseList = new ArrayList<>();
        frAppEnvs.forEach(app -> {
            FrAppEnvVo vo = new FrAppEnvVo();
            BeanUtils.copyProperties(app, vo);
            if (vo.getCreator() == 0) {
                vo.setCreatorName("系统");
            } else {
                vo.setCreatorName(userMap.get(app.getCreator()));
            }
            responseList.add(vo);
        });

        return responseList;
    }

    public void addEnv(String appId, String envId, String envName, Integer creator) {
        FrAppEnv saveData = new FrAppEnv();
        saveData.setAppId(appId);
        saveData.setEnvId(envId);
        saveData.setEnvName(envName);
        saveData.setCreator(creator);
        saveData.setCreatedAt(new Date());
        appEnvMapper.insert(saveData);
    }
}
