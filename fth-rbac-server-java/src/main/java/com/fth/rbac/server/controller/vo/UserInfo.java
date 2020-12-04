package com.fth.rbac.server.controller.vo;

import com.fth.rbac.server.core.entity.FrUser;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Integer status;
    private Date createdAt;
    private String nickname;
    private String phone;
    private Integer admin;

    public static List<UserInfo> covert(List<FrUser> users) {
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo;
        for (FrUser user : users) {
            userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);
            userInfos.add(userInfo);
        }
        return userInfos;
    }
}
