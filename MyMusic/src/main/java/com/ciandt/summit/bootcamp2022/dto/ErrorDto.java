package com.ciandt.summit.bootcamp2022.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
public class ErrorDto {

        private String message;
        private HttpStatus error;
        private String dateTime;
}
