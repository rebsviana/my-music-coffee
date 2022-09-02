package com.ciandt.summit.bootcamp2022.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAuthorizerRequestData {

    private String name;

    private String token;
}
