package com.imine.farouk.controller;

import com.imine.farouk.dto.BankResponse;
import com.imine.farouk.dto.UserDto;
import com.imine.farouk.service.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserDto userDto) {
        return  userService.createAccount(userDto);
    }
}
