package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlaylistDoesntExistOnThisUserException extends RuntimeException{

    public static final String MESSAGE = "Playlist doesn't exist on this user!";

    public PlaylistDoesntExistOnThisUserException(){
        super(MESSAGE);
    }
}
