package com.online.shop.controller;

import com.online.shop.dto.user.RegisterUserResponseDto;
import com.online.shop.entity.Role;
import com.online.shop.entity.User;
import com.online.shop.exception.RequestException;
import com.online.shop.model.binding.user.LoginUserBindingModel;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import com.online.shop.service.UserService;
import com.online.shop.util.ErrorMessageHelper;
import com.online.shop.util.Response;
import com.online.shop.util.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterUserBindingModel registerModel,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()));
        }

        RegisterUserResponseDto respUser = this.userService.register(registerModel);

        return new ResponseEntity<Response>(new Response(ResponseMessageConstants.REGISTER_SUCCESS, respUser), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response> login(@RequestBody LoginUserBindingModel model){

        RegisterUserResponseDto loggedUser = this.userService.login(model);

        return new ResponseEntity<Response>(new Response(ResponseMessageConstants.LOGIN_SUCCESS, loggedUser), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ResponseEntity<Response>(new Response(ResponseMessageConstants.LOGOUT_SUCCESS, ""), HttpStatus.CREATED);
    }



}
