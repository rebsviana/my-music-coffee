package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestPlaylistException extends RuntimeException{

    public BadRequestPlaylistException(String message){
        super(message);
    }
}
