package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static com.ciandt.summit.bootcamp2022.config.Factory.AUTHORIZATION_BAERER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private TokenAuthorizerService tokenAuthorizerService;
    @Mock
    private HttpServletRequest request;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = Factory.createUserDto();
    }

    @Test
    @DisplayName("When save user then return response entity")
    void whenSaveUserThenReturnResponseEntity() {
        ResponseEntity<String> responseEntity= new ResponseEntity<>(Factory.MSG_200_USER_CREATED_SUCCESSFULLY, HttpStatus.OK);

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(responseEntity);

        when(userServiceImpl.saveUser(userDto)).thenReturn(userDto);

        when(request.getHeader("Authorization")).thenReturn(AUTHORIZATION_BAERER);

        var response = userController.saveUser(userDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }
}