package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MusicDoesntExistInPlaylistException extends RuntimeException{

    public static final String MESSAGE = "Music doesn't exist in playlist";

    public MusicDoesntExistInPlaylistException(){
        super(MESSAGE);
    }
}
