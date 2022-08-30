package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequest;
import com.ciandt.summit.bootcamp2022.dto.token.CreateAuthorizerRequestData;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.TokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthorizerService {

    @Autowired
    private TokenProviderService tokenProviderService;

    public void verifyTokenAuthorizer(){
        // TODO: Name and token are fixed
        try {
            tokenProviderService.createTokenAuthorizer
                    (new CreateAuthorizerRequest
                            (new CreateAuthorizerRequestData
                                    ("luana", "fcIiFbpf5KfJidwxVNSKf3aJf4BMkemvXMo+DFWgrwKGM5cY6wyCTcczSPhn/H/Y")));
        } catch (Exception e){
            throw new UnauthorizedAccessException();
        }
    }
}
