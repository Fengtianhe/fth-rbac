package com.fth.rbac.server.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public class SecurityHelper {

    public static String securityPass(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        return hash;
    }

    public static boolean checkPass(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }

    public static Integer userId(HttpServletRequest request) {
        return Integer.parseInt((String) request.getAttribute("userId"));
    }

    public static void main(String[] args) {
        // password: md5("admin123")
        // res: $2a$10$w7s4wWlGw/l9D5BCZdS8l.//5iReLmZ5ykSfqOstg0ygyloR417Di
        System.out.println(SecurityHelper.securityPass("0192023a7bbd73250516f069df18b500"));
    }
}

