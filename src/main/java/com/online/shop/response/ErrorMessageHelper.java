package com.online.shop.response;

import org.springframework.validation.FieldError;

import java.util.List;

public final class ErrorMessageHelper {

    private ErrorMessageHelper(){}

    public static String formatMessage(List<FieldError> errors){
        StringBuilder sb = new StringBuilder();

        for (FieldError error : errors) {
            sb.append(error.getDefaultMessage()).append(System.lineSeparator());
        }

        return sb.toString();
    }

}
