package com.fth.rbac.server.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.DigestUtils;

/**
 * @author 冯天鹤
 * @version 1.0
 * @date 2020/11/24
 * content:
 */
public class SecurityHelper {

    public static String md5(String pass) {
        return DigestUtils.md5DigestAsHex(pass.getBytes());
    }

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

    // 生成密码
    public static String genPass() {
        String[] pswdStr = {"qwertyuiopasdfghjklzxcvbnm",
                "QWERTYUIOPASDFGHJKLZXCVBNM", "0123456789",
                "~!@#$%^&*()_+{}|<>?:{}"};

        int pswdLen = 8;
        String pswd = " ";
        char[] chs = new char[pswdLen];
        for (int i = 0; i < pswdStr.length; i++) {

            int idx = (int) (Math.random() * pswdStr[i].length());
            chs[i] = pswdStr[i].charAt(idx);

        }

        for (int i = pswdStr.length; i < pswdLen; i++) {

            int arrIdx = (int) (Math.random() * pswdStr.length);
            int strIdx = (int) (Math.random() * pswdStr[arrIdx].length());

            chs[i] = pswdStr[arrIdx].charAt(strIdx);
        }

        for (int i = 0; i < 1000; i++) {
            int idx1 = (int) (Math.random() * chs.length);
            int idx2 = (int) (Math.random() * chs.length);

            if (idx1 == idx2) {
                continue;
            }

            char tempChar = chs[idx1];
            chs[idx1] = chs[idx2];
            chs[idx2] = tempChar;
        }

        pswd = new String(chs);

        return pswd;
    }

    public static void main(String[] args) {
        // password: md5("admin123")
        // res: $2a$10$w7s4wWlGw/l9D5BCZdS8l.//5iReLmZ5ykSfqOstg0ygyloR417Di
        System.out.println(SecurityHelper.securityPass("0192023a7bbd73250516f069df18b500"));
    }
}

