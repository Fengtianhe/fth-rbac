package com.fth.rbac.server.service.impl;

import com.fth.rbac.server.controller.vo.EnvVo;
import com.fth.rbac.server.core.entity.FrEnv;
import com.fth.rbac.server.core.entity.FrEnvExample;
import com.fth.rbac.server.core.entity.FrUser;
import com.fth.rbac.server.core.mapper.FrEnvMapper;
import com.fth.rbac.server.service.EnvService;
import com.fth.rbac.server.service.UserService;
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
public class EnvServiceImpl implements EnvService {
    @Autowired
    private FrEnvMapper appEnvMapper;
    @Autowired
    private UserService userService;

    @Override
    public void createDefaultEnv(String appId) {
        this.addEnv(appId, "dev", "开发环境", 0);
        this.addEnv(appId, "stg", "测试环境", 0);
        this.addEnv(appId, "prd", "生产环境", 0);
    }

    @Override
    public List<EnvVo> selectByAppId(String appId) {
        FrEnvExample envExample = new FrEnvExample();
        envExample.createCriteria().andAppIdEqualTo(appId);
        List<FrEnv> FrEnvs = appEnvMapper.selectByExample(envExample);

        if (CollectionUtils.isEmpty(FrEnvs)) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> userIds = FrEnvs.stream().map(FrEnv::getCreator).collect(Collectors.toList());
        List<FrUser> users = userService.selectByIds(userIds);
        Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(FrUser::getId, FrUser::getUsername));

        List<EnvVo> responseList = new ArrayList<>();
        FrEnvs.forEach(app -> {
            EnvVo vo = new EnvVo();
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
        FrEnv saveData = new FrEnv();
        saveData.setAppId(appId);
        saveData.setEnvId(envId);
        saveData.setEnvName(envName);
        saveData.setCreator(creator);
        saveData.setCreatedAt(new Date());
        appEnvMapper.insert(saveData);
    }
}
