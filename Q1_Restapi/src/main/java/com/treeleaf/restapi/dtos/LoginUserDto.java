package com.treeleaf.restapi.dtos;

import com.treeleaf.restapi.entities.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class LoginUserDto {
    @NotNull(message = "username is required")
    @NotEmpty(message = "username  is required")
    private String username;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    private String password;


    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}