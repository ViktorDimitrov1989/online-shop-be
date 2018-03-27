package com.online.shop.areas.users.controllers;
import com.online.shop.areas.users.dto.user.UserResponseDto;
import com.online.shop.areas.users.services.UserService;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/makeAdmin/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Response> makeUserAdminById(@PathVariable Long userId){
        UserResponseDto respUser = this.userService.makeUserAdminById(userId);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.ADMIN, respUser), HttpStatus.OK);
    }

    @RequestMapping(path = "/block/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Response> blockUserById(@PathVariable Long userId){
        UserResponseDto respUser = this.userService.blockUnblockUserById(userId, false);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.BLOCKED, respUser), HttpStatus.OK);
    }

    @RequestMapping(path = "/unblock/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Response> unBlockUserById(@PathVariable Long userId){
        UserResponseDto respUser = this.userService.blockUnblockUserById(userId, true);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.UNBLOCKED, respUser), HttpStatus.OK);
    }

    @RequestMapping(path = "/takeAdmin/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Response> takeAdminPermissionsById(@PathVariable Long userId){
        UserResponseDto respUser = this.userService.takeAdminPrivilegesById(userId);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.ADMIN_PRIVILЕGES_TAKEN, respUser), HttpStatus.OK);
    }

    @RequestMapping(path = "/get", params = { "page", "size" }, method = RequestMethod.GET)
    public ResponseEntity<Response> getAllUsers(HttpSession httpSession, @RequestParam("page") int page, @RequestParam("size") int size){

        Long loggedUserId = Long.parseLong(httpSession.getAttribute("loggedUserId").toString());

        Page<UserResponseDto> users = this.userService.findAllUsers(loggedUserId, page, size);

        return new ResponseEntity<>(new Response("", users), HttpStatus.OK);
    }


}
