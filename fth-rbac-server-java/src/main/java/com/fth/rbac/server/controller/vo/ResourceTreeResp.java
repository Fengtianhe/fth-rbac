package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.entity.FrResource;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/2
 * content:
 */
@Data
public class ResourceTreeResp extends FrResource {
    private List<ResourceTreeResp> children;

    public static List<ResourceTreeResp> covert(List<FrResource> resources) {
        List<ResourceTreeResp> resourceTreeVo = new ArrayList<>();
        ResourceTreeResp vo;
        for (FrResource res : resources) {
            vo = new ResourceTreeResp();
            BeanUtils.copyProperties(res, vo);
            resourceTreeVo.add(vo);
        }
        return resourceTreeVo;
    }
}
