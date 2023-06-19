package com.numble.reservationsystem.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.numble.reservationsystem.controller"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Arrays.asList(apiKey(), apiRefreshKey()))
            .securityContexts(Arrays.asList(securityContext()))
            .apiInfo(apiInfo())
            ;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
            "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
            new SecurityReference("access", authorizationScopes),
            new SecurityReference("refresh", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("access", "access", "header");
    }

    private ApiKey apiRefreshKey() {
        return new ApiKey("refresh", "refresh", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Reservation System API")
            .description("예매 시스템 API 명세")
            .version("1.0.0")
            .build();
    }
}
