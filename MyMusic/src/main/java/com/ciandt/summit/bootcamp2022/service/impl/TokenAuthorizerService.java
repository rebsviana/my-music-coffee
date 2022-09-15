package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequestData;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Log4j2
public class TokenAuthorizerService {

    @Autowired
    private TokenProviderService tokenProviderService;

    public ResponseEntity<String> verifyTokenAuthorizer(String requestAuthorization){
        try {
            var authorizationEncode = requestAuthorization.substring(7);
            var authorizationDecode = Base64.getDecoder().decode(authorizationEncode);
            var authorization = new String(authorizationDecode);
            var user = authorization.split(":")[0];
            var password = authorization.split(":")[1];

            log.info("Starting integration with API Token Provider Service");

            return tokenProviderService.createTokenAuthorizer
                    (new CreateAuthorizerRequest
                            (new CreateAuthorizerRequestData
                                    (user, password)));
        } catch (Exception e){
            throw new UnauthorizedAccessException();
        }
    }
}
