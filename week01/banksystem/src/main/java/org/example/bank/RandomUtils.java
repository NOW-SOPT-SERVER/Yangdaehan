package org.example.bank;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static String generateAccountNum(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < 12; i++) {
            int random = RANDOM.nextInt(10);
            sb.append(random);
        }

        return sb.toString();
    }
}
