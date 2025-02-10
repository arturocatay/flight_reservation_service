package com.vortech.flight.reservation.util;

import java.security.SecureRandom;

public class HexGenerator {

    public static String generateKey(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[length];
        secureRandom.nextBytes(key);

        StringBuilder hexKey = new StringBuilder();
        for (byte b : key) {
            hexKey.append(String.format("%02x", b));
        }

        return hexKey.toString().toUpperCase();
    }
}
