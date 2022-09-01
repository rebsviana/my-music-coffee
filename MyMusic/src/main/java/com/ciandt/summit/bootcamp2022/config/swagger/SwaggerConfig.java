package com.ciandt.summit.bootcamp2022.config.swagger;

import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private final ResponseMessage MSG_200 = simpleMessage(200, "OK");
    private final ResponseMessage MSG_201 = simpleMessage(201, "Created");
    private final ResponseMessage MSG_NO_CONTENT_204 = simpleMessage(204, NoContentException.MESSAGE);
    private final ResponseMessage MSG_MIN_LENGTH_400 = simpleMessage(404, MinLengthRequiredException.MESSAGE);
    private final ResponseMessage MSG_BAD_REQUEST_400 = simpleMessage(400, "Music or Playslit doesn't exist");
    private final ResponseMessage MSG_500 = simpleMessage(500, "Internal Server Error");

    private ResponseMessage simpleMessage(int code, String message) {
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(MSG_200, MSG_NO_CONTENT_204, MSG_MIN_LENGTH_400, MSG_500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(MSG_201, MSG_BAD_REQUEST_400, MSG_500))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ciandt.summit.bootcamp2022.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
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
}