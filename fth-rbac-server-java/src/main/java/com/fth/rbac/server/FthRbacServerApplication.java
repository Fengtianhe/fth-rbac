package com.fth.rbac.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author fengtianhe
 */

@SpringBootApplication
@MapperScan({
        "com.fth.rbac.server.core.mapper",
        "com.fth.rbac.server.core.mapper.ext"
})
public class FthRbacServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FthRbacServerApplication.class, args);
    }
}
