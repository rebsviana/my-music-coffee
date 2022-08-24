package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.ErrorDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MinLengthRequiredException.class)
    public ResponseEntity<ErrorDto> handleMinRequiredException(MinLengthRequiredException e) {
        log.error("The name must have three or more than three characters");
        var errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .error(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(value = NoContentException.class)
    public ResponseEntity<ErrorDto> handleNoContentException(NoContentException e) {
        log.error("No results found!");
        var errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .error(HttpStatus.NO_CONTENT)
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorDto);
    }

    @ExceptionHandler(value = UnauthorizedAccessException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        log.error("You have not the rights to access this function!");
        var errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .error(HttpStatus.UNAUTHORIZED)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

}
