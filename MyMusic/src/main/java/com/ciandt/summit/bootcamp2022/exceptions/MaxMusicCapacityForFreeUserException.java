package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxMusicCapacityForFreeUserException extends RuntimeException{

    public static final String MESSAGE = "Reached Max Music Capacity for Free ";

    public MaxMusicCapacityForFreeUserException() { super(MESSAGE); }
}
