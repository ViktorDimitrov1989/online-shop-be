package com.online.shop.controller;
import com.online.shop.exception.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    public ResponseEntity<ExceptionResponseDto> throwException(){
        return new ResponseEntity<>(new ExceptionResponseDto("Нямате права за тази функционалност"), HttpStatus.OK);
    }
}
