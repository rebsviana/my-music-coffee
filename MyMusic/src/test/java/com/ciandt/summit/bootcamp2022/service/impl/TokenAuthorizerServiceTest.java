package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ciandt.summit.bootcamp2022.config.Factory.AUTHORIZATION_BAERER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TokenAuthorizerServiceTest {
    @Mock
    private TokenProviderService tokenProviderService;

    @InjectMocks
    private TokenAuthorizerService tokenAuthorizerService;

    @Test
    @DisplayName("When verify token provider then return HttpStatus Created")
    void whenVerifyTokenProviderThenReturnResponseEntity() {
        ResponseEntity<String> responseEntity= new ResponseEntity<>(Factory.MSG_200_OK, HttpStatus.CREATED);

        when(tokenProviderService.createTokenAuthorizer(any(CreateAuthorizerRequest.class)))
                .thenReturn(responseEntity);

        var response = tokenAuthorizerService.verifyTokenAuthorizer(AUTHORIZATION_BAERER);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Factory.MSG_200_OK, response.getBody());
    }

    @Test
    @DisplayName("When verify wrong token provider then return UnauthorizedAccessException")
    void whenVerifyWrongTokenProviderThenReturnUnauthorizedAccessException() {
        when(tokenProviderService.createTokenAuthorizer(any(CreateAuthorizerRequest.class)))
                .thenThrow(new UnauthorizedAccessException());

        var exception = assertThrows(UnauthorizedAccessException.class,
                () -> tokenAuthorizerService.verifyTokenAuthorizer(AUTHORIZATION_BAERER));

        assertNotNull(exception);
        assertEquals(UnauthorizedAccessException.MESSAGE, exception.getMessage());
        assertEquals(UnauthorizedAccessException.class, exception.getClass());
    }
}