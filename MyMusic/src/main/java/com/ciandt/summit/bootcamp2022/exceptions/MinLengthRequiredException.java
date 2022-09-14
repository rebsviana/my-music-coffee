package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.ciandt.summit.bootcamp2022.config.Factory;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MinLengthRequiredException extends RuntimeException{

    public static final String MESSAGE = Factory.MSG_400_NAME_HAVE_LESS_THAN_THREE_CHARACTERS;

    public MinLengthRequiredException(){
        super(MESSAGE);
    }

}
