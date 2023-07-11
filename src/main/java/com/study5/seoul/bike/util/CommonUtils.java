package com.study5.seoul.bike.util;

import java.util.Random;

public class CommonUtils {

    public static String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
