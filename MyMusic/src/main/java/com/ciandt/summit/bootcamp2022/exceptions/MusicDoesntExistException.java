package com.ciandt.summit.bootcamp2022.exceptions;

import com.ciandt.summit.bootcamp2022.config.Factory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MusicDoesntExistException extends RuntimeException{

    public static final String MESSAGE = Factory.MSG_400_MUSIC_DOESNT_EXIST;

    public MusicDoesntExistException(){
        super(MESSAGE);
    }
}
