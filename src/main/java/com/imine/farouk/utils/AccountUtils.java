package com.imine.farouk.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;


public class AccountUtils {


    public static final  String ACCOUNT_EXISTS_CODE = "001";
    public static final  String ACCOUNT_EXISTS_MESSAGE = "THIS USER ALREADY HAS AN ACCOUNT CREATED!";
    public static final  String ACCOUNT_CREATION_CODE = "002";
    public static final  String ACCOUNT_CREATION_MESSAGE = "ACCOUNT HAS BEEN SUCCESSFULLY CREATED!";


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
