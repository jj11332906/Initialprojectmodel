package com.wkj.project.config.swagger;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by xun on 2017/12/23.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("demo")
    String applicationName;

    @Value("1.0")
    String applicationVersion;

    @Value("${spring.profiles.active}")
    String profile;

//    @Value("${spring.application.build}")
//    String applicationBuild;

    @Bean
    public Docket createAllRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("1-all 全部API")
                .apiInfo(new ApiInfoBuilder().title(applicationName + " API ALL - " + profile)
                        .version(applicationVersion)
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();
    }

    @Bean
    public Docket createCompanyRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("2-dingding 钉钉")
                .apiInfo(new ApiInfoBuilder().title(applicationName + " API 钉钉 - " + profile)
                        .version(applicationVersion)
                        .build())
                .select()
                .paths(PathSelectors.ant("/dingding/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();
    }

    @Bean
    public Docket createProducerRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("3-admin 管理后台")
                .apiInfo(new ApiInfoBuilder().title(applicationName + " API 管理后台 - " + profile)
                        .version(applicationVersion)
                        .build())
                .select()
                .paths(PathSelectors.ant("/admin/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();
    }
}
