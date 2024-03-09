package com.example.monywell.utils;

import java.util.Random;

public class AccountNumberGenerator {
    private static Random accountNumber = new Random();

    public static String getAccountNumber() {

        Long number = accountNumber.nextLong(99999999, 999999999);

        return String.valueOf(number);
    }
}
