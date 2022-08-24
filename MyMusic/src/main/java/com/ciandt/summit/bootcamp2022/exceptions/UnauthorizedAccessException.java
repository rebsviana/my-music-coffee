package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends RuntimeException{

    private static final String MESSAGE = "You have not the rights to access this function!";

    public UnauthorizedAccessException(){
        super(MESSAGE);
    }
}
