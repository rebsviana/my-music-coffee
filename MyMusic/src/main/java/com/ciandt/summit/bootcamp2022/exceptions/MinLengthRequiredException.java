package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MinLengthRequiredException extends RuntimeException{

    private static final String MESSAGE = "The name must have three or more than three characters";

    public MinLengthRequiredException(){
        super(MESSAGE);
    }

}
