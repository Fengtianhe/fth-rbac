package com.fth.rbac.server.controller.vo;

import lombok.Data;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/12/3
 * content:
 */
@Data
public class UserUpdateReq {
    private String nickname;

    private String phone;
}
