package com.charita.event_platform.controller;

import com.charita.event_platform.dto.LoginRequest;
import com.charita.event_platform.dto.LoginResponse;
import com.charita.event_platform.model.User;
import com.charita.event_platform.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        return userService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/profile")
    public String profile() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
