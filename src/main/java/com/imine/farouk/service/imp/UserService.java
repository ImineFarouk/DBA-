package com.imine.farouk.service.imp;

import com.imine.farouk.dto.BankResponse;
import com.imine.farouk.dto.UserDto;

public interface UserService {

    BankResponse createAccount(UserDto userDto);
}
