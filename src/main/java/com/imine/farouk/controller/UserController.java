package com.imine.farouk.controller;

import com.imine.farouk.dto.BankResponse;
import com.imine.farouk.dto.CreditDebitDto;
import com.imine.farouk.dto.EnquiryDto;
import com.imine.farouk.dto.UserDto;
import com.imine.farouk.service.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserDto userDto) {
        return  userService.createAccount(userDto);
    }

    @GetMapping("balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryDto enquiryDto) {
        return userService.balanceEnquiry(enquiryDto);
    }

    @GetMapping("nameEnquiry")
    public  String nameEnquiry(@RequestBody EnquiryDto enquiryDto){
        return userService.nameEnquiry(enquiryDto);
    }

    @PostMapping("credit")
    public BankResponse creditRequest(@RequestBody CreditDebitDto creditDebitDto){
        return userService.creditAccount(creditDebitDto);
    }

    @PostMapping("debit")
    public BankResponse debitRequest(@RequestBody CreditDebitDto creditDebitDto){
        return userService.debitAccount(creditDebitDto);
    }
}
