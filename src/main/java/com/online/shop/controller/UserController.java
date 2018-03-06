package com.online.shop.controller;

import com.online.shop.dto.user.RegisterUserResponseDto;
import com.online.shop.exception.RequestException;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import com.online.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserBindingModel registerModel,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            for (FieldError err : bindingResult.getFieldErrors()) {
                System.out.println(err.getDefaultMessage());
            }

            throw new RequestException("Passwords mismatch");
        }

        return new ResponseEntity<RegisterUserResponseDto>(this.userService.register(registerModel), HttpStatus.CREATED);
    }




    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String register(){

        //register user through the service

        return null;
    }





}
