package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequestData;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TokenAuthorizerService {

    @Autowired
    private TokenProviderService tokenProviderService;

    public ResponseEntity<String> verifyTokenAuthorizer(String userName, String userToken){
        try {
            log.info("Starting integration with API Token Provider Service");

            return tokenProviderService.createTokenAuthorizer
                    (new CreateAuthorizerRequest
                            (new CreateAuthorizerRequestData
                                    (userName, userToken)));
        } catch (Exception e){
            throw new UnauthorizedAccessException();
        }
    }
}
