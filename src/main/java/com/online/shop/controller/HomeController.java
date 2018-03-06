package com.online.shop.controller;

import com.online.shop.model.user.RegisterUserBindingModel;
import com.online.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute RegisterUserBindingModel model,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //exception
        }

        //register user through the service

        return null;
    }

    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String register(){

        //register user through the service

        return null;
    }





}
