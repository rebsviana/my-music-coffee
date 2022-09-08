package com.ciandt.summit.bootcamp2022.config.swagger;

import lombok.Generated;
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
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Generated
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                            .name("name")
//                            .description("Name Authorization")
//                            .modelRef(new ModelRef("string"))
//                            .parameterType("header")
//                            .required(true)
//                            .build(),
//                        new ParameterBuilder()
//                            .name("token")
//                            .description("Token Authorization")
//                            .modelRef(new ModelRef("string"))
//                            .parameterType("header")
//                            .required(true)
//                            .build()))
                .useDefaultResponseMessages(false)
                .directModelSubstitute(Object.class, java.lang.Void.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ciandt.summit.bootcamp2022.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API MyMusic")
                .description("API MyMusic - Group Coffee")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "HTTP_REQUEST_API_KEY", "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/rest/*"))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("api-client-id")
                .clientSecret("api-client-secret")
                .realm("api-realm")
                .appName("api-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }
}