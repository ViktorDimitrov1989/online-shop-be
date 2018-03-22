package com.online.shop.exception;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ExceptionResponseDto> conflict(RequestException ex) {
        return new ResponseEntity<ExceptionResponseDto>(new ExceptionResponseDto(ex.getMessage()), ex.getHttpStatus());
    }

}
