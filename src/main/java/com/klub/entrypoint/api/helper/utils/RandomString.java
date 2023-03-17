package com.klub.entrypoint.api.helper.utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random alphanumeric string
     * @param n the size of the string to generate
     * @return the random string
     */
    public static String getAlphaNumericString(int n) {
        byte[] array = new byte[256];
        (new Random()).nextBytes(array);

        String randomString
                = new String(array, StandardCharsets.UTF_8);

        StringBuilder r = new StringBuilder();

        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        return r.toString();
    }
}
