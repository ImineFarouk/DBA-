package com.imine.farouk.service.imp;

import com.imine.farouk.dto.AccountInfo;
import com.imine.farouk.dto.BankResponse;
import com.imine.farouk.dto.UserDto;
import com.imine.farouk.entity.User;
import com.imine.farouk.repository.UserRepository;
import com.imine.farouk.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public BankResponse createAccount(UserDto userDto) {

        /**
         * Creating an account - saving a new user info into the db
         * Check if user already has an account
         */
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                .accountInfo(null)
                .build();
        }
        User newUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .otherName(userDto.getOtherName())
                .gender(userDto.getGender())
                .address(userDto.getAddress())
                .stateOfOrigin(userDto.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .alternativePhoneNumber(userDto.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName()+" "+savedUser.getLastName()+" "+savedUser.getOtherName())
                        .build())
                .build();
    }


}
