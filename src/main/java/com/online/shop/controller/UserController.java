package com.online.shop.controller;

import com.online.shop.dto.user.RegisterUserResponseDto;
import com.online.shop.exception.RequestException;
import com.online.shop.model.binding.user.LoginUserBindingModel;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import com.online.shop.service.UserService;
import com.online.shop.util.Response;
import com.online.shop.util.ResponseMessageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin//(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterUserBindingModel registerModel,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            for (FieldError err : bindingResult.getFieldErrors()) {
                System.out.println(err.getDefaultMessage());
            }

            throw new RequestException(ResponseMessageConstants.PASSWORDS_MISMATCH);
        }

        RegisterUserResponseDto userProfileModel = this.userService.register(registerModel);

        return new ResponseEntity<Response>(new Response(ResponseMessageConstants.REGISTER_SUCCESS, userProfileModel), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response> login(@RequestBody LoginUserBindingModel model){

        RegisterUserResponseDto userProfileModel = this.userService.login(model);

        return new ResponseEntity<Response>(new Response(ResponseMessageConstants.LOGIN_SUCCESS, userProfileModel), HttpStatus.CREATED);
    }





}
