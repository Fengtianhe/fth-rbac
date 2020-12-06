package com.fth.rbac.server.swagger;

import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created on 2020/12/6 1:56 下午.
 *
 * @author fengtianhe
 */

public class DocsApi {
    public static Docket docs() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台API")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fth.rbac.server.controller"))
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线接口文档")
                .description("本文档只针对系统中的HTTP接口")
                .termsOfServiceUrl("")
                .version("20201201")
                .build();
    }
}
