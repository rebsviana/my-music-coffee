package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequestData;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TokenAuthorizerServiceTest {

    @Mock
    private TokenProviderService tokenProviderService;

    @InjectMocks
    private TokenAuthorizerService tokenAuthorizerService;

    private CreateAuthorizerRequest createAuthorizerRequest;

    public static final String NAME_TOKEN = "Bruno";

    public static final String TOKEN = "123456789";

    @BeforeEach
    void setup() {
        createAuthorizerRequest = new CreateAuthorizerRequest(new CreateAuthorizerRequestData
                        ("luana", "kl4Gcpc+e+m2uJ+MgEpOSkAOrR/wlu75srgGH0UUyJRulXmf5jeX/xalLosGqI9s"));
    }

    @Disabled
    @Test
    void verifyTokenAuthorizer() {
        ResponseEntity<String> responseEntity= new ResponseEntity<>("ok", HttpStatus.CREATED);

        when(tokenProviderService.createTokenAuthorizer(createAuthorizerRequest))
                .thenReturn(responseEntity);

        var response = tokenAuthorizerService.verifyTokenAuthorizer(NAME_TOKEN, TOKEN);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals("ok", response.getBody());
    }

    @Disabled
    @Test
    void verifyTokenAuthorizerReturnException() {
        when(tokenProviderService.createTokenAuthorizer(createAuthorizerRequest))
                .thenThrow(new UnauthorizedAccessException());

        var exception = assertThrows(UnauthorizedAccessException.class,
                () -> tokenAuthorizerService.verifyTokenAuthorizer(NAME_TOKEN, TOKEN));

        assertNotNull(exception);
        assertEquals(UnauthorizedAccessException.MESSAGE, exception.getMessage());
        assertEquals(UnauthorizedAccessException.class, exception.getClass());
    }
}