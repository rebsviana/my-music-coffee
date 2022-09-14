package com.ciandt.summit.bootcamp2022.exceptions;

import com.ciandt.summit.bootcamp2022.config.Factory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends RuntimeException{

    public static final String MESSAGE = Factory.MSG_401_NO_RIGHTS;

    public UnauthorizedAccessException(){
        super(MESSAGE);
    }
}
