package com.imine.farouk.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;


public class AccountUtils {


    public static final  String ACCOUNT_EXISTS_CODE = "001";
    public static final  String ACCOUNT_EXISTS_MESSAGE = "THIS USER ALREADY HAS AN ACCOUNT CREATED!";
    public static final  String ACCOUNT_CREATION_CODE = "002";
    public static final  String ACCOUNT_CREATION_MESSAGE = "ACCOUNT HAS BEEN SUCCESSFULLY CREATED!";
    public static final  String ACCOUNT_DOES_NOT_EXISTS_CODE = "003";
    public static final  String ACCOUNT_DOES_NOT_EXISTS_MESSAGE = "THE USER WITH THE PROVIDED ACCOUNT NUMBER DOES NOT EXISTS!";
    public static final  String ACCOUNT_FOUND_CODE = "004";
    public static final  String ACCOUNT_FOUND_MESSAGE = "USER ACCOUNT FOUND";
    public static final  String ACCOUNT_CREDIT_CODE = "005";
    public static final String ACCOUNT_CREDIT_MESSAGE = "ACCOUNT CREDITED SUCCESSFULLY";
    public static final String INSUFFICIENT_FUNDS_CODE = "006";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "INSUFFICIENT FUNDS";
    public static final String ACCOUNT_DEBIT_CODE = "007";
    public static final String ACCOUNT_DEBIT_MESSAGE = "ACCOUNT DEBITED SUCCESSFULLY";




    public static String generateAccountNumber() {
        /**
         * 2025 + randomSixDigits + ActualTime
         */

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //generate a random number between min & max
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        //convert the current year and the random number to strings then concatinate them

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        return year + randomNumber;
    }


}
