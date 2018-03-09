package com.online.shop.exception;

import com.online.shop.util.Response;

public class ExceptionResponseDto {

    private String message;

    public ExceptionResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
