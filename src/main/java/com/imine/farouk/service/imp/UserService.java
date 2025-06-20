package com.imine.farouk.service.imp;

import com.imine.farouk.dto.BankResponse;
import com.imine.farouk.dto.CreditDebitDto;
import com.imine.farouk.dto.EnquiryDto;
import com.imine.farouk.dto.UserDto;

public interface UserService {

    BankResponse createAccount(UserDto userDto);
    BankResponse balanceEnquiry(EnquiryDto enquiryDto);
    String nameEnquiry(EnquiryDto enquiryDto);
    BankResponse creditAccount(CreditDebitDto creditDebitDto);
    BankResponse debitAccount(CreditDebitDto creditDebitDto);
}
