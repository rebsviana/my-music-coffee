package com.ciandt.summit.bootcamp2022.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesntExistException extends RuntimeException {

    public static final String MESSAGE = "User doesn't exist";

    public UserDoesntExistException() { super(MESSAGE); }
}
