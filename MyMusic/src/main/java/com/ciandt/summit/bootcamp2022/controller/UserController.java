package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@Log4j2
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;
    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "Save new user", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = Factory.MSG_200_USER_CREATED_SUCCESSFULLY, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = Factory.MSG_400_USER_ALREADY_EXIST, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = UnauthorizedAccessException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = Factory.MSG_500, content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto){
        log.info("Starting the route save user");
        tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
        log.info("User authenticated successfully");

        userServiceImpl.saveUser(userDto);

        return ResponseEntity.ok(Factory.MSG_200_USER_CREATED_SUCCESSFULLY);
    }

    @Operation(summary = "Get user by nickname", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = Factory.MSG_200_OK, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = Factory.MSG_400_USER_DOESNT_EXIST, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = UnauthorizedAccessException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = Factory.MSG_500, content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/{nickname}")
    public ResponseEntity<PageDecoratorDto<UserDto>> getUserByNickname(@PathVariable String nickname){
        log.info("Starting the route save user");
        tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
        log.info("User authenticated successfully");

        var userDto = userServiceImpl.getUserByNickname(nickname);
        List<UserDto> userDtoList = Collections.singletonList(userDto);

        return ResponseEntity.ok(new PageDecoratorDto<>(new PageImpl<>(userDtoList)));
    }
}
