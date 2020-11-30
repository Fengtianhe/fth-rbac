package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.FrAppEnvVo;

import java.util.List;

/**
 * Created on 2020/11/30 7:59 下午.
 *
 * @author fengtianhe
 */
public interface AppEnvService {
    /**
     * 创建默认的环境dev, stg, prd
     *
     * @param appId
     */
    void createDefaultEnv(String appId);

    /**
     * 查询某个应用的环境
     *
     * @param appId
     * @return
     */
    List<FrAppEnvVo> selectByAppId(String appId);
}
