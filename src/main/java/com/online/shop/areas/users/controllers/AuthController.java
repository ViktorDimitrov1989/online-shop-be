package com.online.shop.areas.users.controllers;

import com.online.shop.areas.users.dto.user.UserResponseDto;
import com.online.shop.exception.RequestException;
import com.online.shop.areas.users.models.binding.user.LoginUserBindingModel;
import com.online.shop.areas.users.models.binding.user.RegisterUserBindingModel;
import com.online.shop.areas.users.services.UserService;
import com.online.shop.response.ErrorMessageHelper;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        UserResponseDto respUser = this.userService.register(registerModel);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.REGISTER_SUCCESS, respUser), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response> login(@RequestBody LoginUserBindingModel model, HttpSession httpSession){

        UserResponseDto loggedUser = this.userService.login(model);

        httpSession.setAttribute("loggedUserId", loggedUser.getId());

        return new ResponseEntity<>(new Response(ResponseMessageConstants.LOGIN_SUCCESS, loggedUser), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ResponseEntity<>(new Response(ResponseMessageConstants.LOGOUT_SUCCESS, ""), HttpStatus.CREATED);
    }



}
