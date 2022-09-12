package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Log4j2
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Save user", notes = "Saved new user")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Factory.MSG_200_USER_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 400, message = Factory.MSG_400_USER_ALREADY_EXIST),
            @ApiResponse(code = 500, message = Factory.MSG_500)
    })
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto,
                                           @RequestHeader(value="name") String userName,
                                           @RequestHeader(value="token") String userToken){
        log.info("Starting the route save user");
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);

        userServiceImpl.saveUser(userDto);

        return ResponseEntity.ok(Factory.MSG_200_USER_CREATED_SUCCESSFULLY);
    }

    @ApiOperation(value = "Get user", notes = "Get user by nickname")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Factory.MSG_200_USER_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 400, message = Factory.MSG_400_USER_ALREADY_EXIST),
            @ApiResponse(code = 500, message = Factory.MSG_500)
    })
    @GetMapping("/{nickname}")
    public ResponseEntity<PageDecoratorDto<UserDto>> getUserByNickname(@PathVariable String nickname,
                                                              @RequestHeader(value="name") String userName,
                                                              @RequestHeader(value="token") String userToken){
        log.info("Starting the route get user");
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);

        var response = userServiceImpl.getUserByNickname(nickname);
        List<UserDto> userDtoList = Collections.singletonList(response);

        return ResponseEntity.ok(new PageDecoratorDto<>(new PageImpl<>(userDtoList)));
    }
}
