package com.example.sce.helper;

import java.security.SecureRandom;

public class OTPGenerator {

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
}
