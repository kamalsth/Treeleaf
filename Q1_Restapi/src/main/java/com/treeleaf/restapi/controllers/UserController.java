package com.treeleaf.restapi.controllers;

import com.treeleaf.restapi.dtos.LoginUserDto;
import com.treeleaf.restapi.dtos.RegisterUserDto;
import com.treeleaf.restapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterUserDto registerRequest) {
        return userService.registerUser(registerRequest.mapToUser());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Validated LoginUserDto loginRequest) {
        return userService.loginUser(loginRequest.toUser());
    }
}
