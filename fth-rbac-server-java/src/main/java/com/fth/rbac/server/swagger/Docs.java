package com.fth.rbac.server.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created on 2020/12/6 1:55 下午.
 *
 * @author fengtianhe
 */
@Configuration
@EnableSwagger2
public class Docs implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return DocsApi.docs();
    }

    @Bean
    public Docket sdk() {
        return DocsSdk.docs();
    }
}
