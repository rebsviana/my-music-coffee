package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.ErrorDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    @DisplayName("When argument size is less than three then return MinLengthRequiredException ")
    void whenLenghtLessThenThreeThenReturnResponseEntity(){

        ResponseEntity<ErrorDto> error = globalExceptionHandler
                .handleMinRequiredException(new MinLengthRequiredException());

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        assertEquals(400, error.getStatusCodeValue());
        assertEquals(MinLengthRequiredException.MESSAGE, error.getBody().getMessage());
    }

    @Test
    @DisplayName("When argument is not found on the Database then return NoContentException")
    void whenArgumentNotFoundThenReturnResponseEntity(){

        ResponseEntity<ErrorDto> error = globalExceptionHandler
                .handleNoContentException(new NoContentException());

        assertEquals(HttpStatus.NO_CONTENT, error.getStatusCode());
        assertEquals(204, error.getStatusCodeValue());
        assertEquals(NoContentException.MESSAGE, error.getBody().getMessage());
    }

    @Test
    @DisplayName("When user has not the rights then return UnauthorizedAccessException")
    void whenUserHasNotTheRightsThenReturnResponseEntity(){

        ResponseEntity<ErrorDto> error = globalExceptionHandler
                .handleUnauthorizedAccessException(new UnauthorizedAccessException());

        assertEquals(HttpStatus.UNAUTHORIZED, error.getStatusCode());
        assertEquals(401, error.getStatusCodeValue());
        assertEquals(UnauthorizedAccessException.MESSAGE, error.getBody().getMessage());
    }
}