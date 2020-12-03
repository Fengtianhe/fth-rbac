package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.entity.FrEnv;
import lombok.Data;

/**
 * Created on 2020/11/30 8:32 下午.
 *
 * @author fengtianhe
 */
@Data
public class EnvVo extends FrEnv {
    private String creatorName;
}
