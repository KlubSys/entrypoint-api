package com.klub.entrypoint.api.helper.utils;

public class CamelCaseUtils {

    /**
     * Convert a string in snake string format into a camel case
     *
     * @param str             the string to convert
     * @param firstCapitalize specifies if the first character needs to be
     *                        capitalized or not
     * @return a string in a camel case format
     */
    public static String fromSnakeCase(String str, boolean firstCapitalize) {

        if (firstCapitalize) {
            // Capitalize first letter of string
            str = str.substring(0, 1).toUpperCase()
                    + str.substring(1);
        } else {
            str = str.charAt(0) + str.substring(1);
        }

        StringBuilder builder = new StringBuilder(str);

        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {
            // Check char is underscore
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        // Return in String type
        return builder.toString();
    }

    /**
     * Convert a string in snake string format into a camel case.
     * The first character will not be capitalized
     *
     * @param str the string to convert
     * @return a string in a camel case format
     */
    public static String fromSnakeCase(String str) {
        return fromSnakeCase(str, true);
    }

    /**
     * Convert a string in camel case format to snake format
     *
     * @param str the string to convert
     * @return a string in snake format
     */
    public static String toSnakeCase(String str) {
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";
        str = str
                .replaceAll(
                        regex, replacement)
                .toLowerCase();
        return str;
    }
}
