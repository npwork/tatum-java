package io.tatum.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericUtil {

    private NumericUtil() {}

    /**
     * The character class \p{XDigit} matches any hexadecimal character.
     */
    private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("\\p{XDigit}+");

    public static boolean isHexadecimal(String input) {
        final Matcher matcher = HEXADECIMAL_PATTERN.matcher(input);
        return matcher.matches();
    }
}
