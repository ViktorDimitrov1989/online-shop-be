package com.online.shop.exception;

import com.online.shop.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ExceptionResponseDto> conflict(RequestException ex) {
        return new ResponseEntity<ExceptionResponseDto>(new ExceptionResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
