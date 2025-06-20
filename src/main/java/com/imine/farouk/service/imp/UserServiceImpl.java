package com.imine.farouk.service.imp;

import com.imine.farouk.dto.*;
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

    @Autowired
    EmailService emailService;

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

        //Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATED")
                .messageBody("Congratulations! Your Account Has Been Successfullu Created.\nYour Account Details:\n"+
                        "Account Name: "+ savedUser.getFirstName()+" "+savedUser.getLastName()+" "+savedUser.getOtherName()+"\nAccountNumber: "+savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);


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

    // balance Enquiry, Name Enquiry, Credit, Debit, Transfer

    @Override
    public BankResponse balanceEnquiry(EnquiryDto enquiryDto) {
        //Check if the provided account number exists in the db
        if(userRepository.existsByAccountNumber(enquiryDto.getAccountNumber())==false){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(enquiryDto.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(foundUser.getFirstName()+" "+foundUser.getLastName()+" "+foundUser.getOtherName())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountBalance(foundUser.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryDto enquiryDto) {
        if (!userRepository.existsByAccountNumber(enquiryDto.getAccountNumber())) {
            return AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryDto.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitDto creditDebitDto) {
        //Check if the account exists
        if(userRepository.existsByAccountNumber(creditDebitDto.getAccountNumber())==false){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitDto.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitDto.getAmount()));
        userRepository.save(userToCredit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDIT_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDIT_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName()+" "+userToCredit.getLastName()+" "+userToCredit.getOtherName())
                        .accountNumber(userToCredit.getAccountNumber())
                        .accountBalance(userToCredit.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitDto creditDebitDto) {
        //Check if the account exists
        if(userRepository.existsByAccountNumber(creditDebitDto.getAccountNumber())==false){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToDebit = userRepository.findByAccountNumber(creditDebitDto.getAccountNumber());
        //Check if the user has enough credit
        if(creditDebitDto.getAmount().compareTo(userToDebit.getAccountBalance())>0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitDto.getAmount()));
        userRepository.save(userToDebit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBIT_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName()+" "+userToDebit.getOtherName())
                        .accountNumber(userToDebit.getAccountNumber())
                        .accountBalance(userToDebit.getAccountBalance())
                        .build())
                .build();
    }


}
