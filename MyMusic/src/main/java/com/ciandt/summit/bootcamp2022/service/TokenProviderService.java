package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.model.token.CreateAuthorizerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tokenprovider", url = "http://localhost:8081/api/v1/token/authorizer")
public interface TokenProviderService {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTokenAuthorizer(@RequestBody CreateAuthorizerRequest createAuthorizerRequest);
}
