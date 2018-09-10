package com.tousie.securities.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randoms {

    public static String ofRange(Random random, String range, int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = range.charAt(random.nextInt(length));
        }
        return new String(result);
    }

    public static String ofRange(String range, int length) {
        return ofRange(ThreadLocalRandom.current(), range, length);
    }
}
