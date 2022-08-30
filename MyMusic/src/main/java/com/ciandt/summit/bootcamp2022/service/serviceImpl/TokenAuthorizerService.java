package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequestData;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthorizerService {

    @Autowired
    private TokenProviderService tokenProviderService;

    public ResponseEntity<String> verifyTokenAuthorizer(){
        // TODO: Name and token are fixed
        try {
            return tokenProviderService.createTokenAuthorizer
                    (new CreateAuthorizerRequest
                            (new CreateAuthorizerRequestData
                                    ("luana", "cIiFbpf5KfJidwxVNSKf3aJf4BMkemvXMo+DFWgrwKGM5cY6wyCTcczSPhn/H/Y")));
        } catch (Exception e){
            throw new UnauthorizedAccessException();
        }
    }
}
