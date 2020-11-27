package com.fth.rbac.server.controller.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/25
 * content:
 */
@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String status;
    private Date createdAt;
}
