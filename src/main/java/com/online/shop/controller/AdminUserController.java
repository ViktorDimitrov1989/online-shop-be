package com.online.shop.controller;


import com.online.shop.dto.user.UserResponseDto;
import com.online.shop.service.UserService;
import com.online.shop.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/edit/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Response> editUserRoles(@PathVariable Long userId){
        return  null;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllUsers(){
        List<UserResponseDto> users = this.userService.findAllUsers();

        return new ResponseEntity<>(new Response("", users), HttpStatus.OK);
    }


}
