package com.fth.rbac.server.service;

import com.fth.rbac.server.controller.vo.SaveResourceReq;

/**
 * Created on 2020/12/1 10:26 下午.
 *
 * @author fengtianhe
 */
public interface AppResourceService {
    /**
     * 创建资源
     *
     * @param userId
     * @param saveResourceReq
     * @return
     */
    String create(Integer userId, SaveResourceReq saveResourceReq);
}
