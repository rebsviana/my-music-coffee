package com.ciandt.summit.bootcamp2022.dto.token;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAuthorizerRequest {

    private CreateAuthorizerRequestData data;

    public CreateAuthorizerRequestData getData() {
        return data;
    }

    public void setData(CreateAuthorizerRequestData data) {
        this.data = data;
    }
}
