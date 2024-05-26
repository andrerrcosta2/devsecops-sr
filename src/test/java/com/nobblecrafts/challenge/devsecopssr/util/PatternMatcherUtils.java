package com.nobblecrafts.challenge.devsecopssr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcherUtils {

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false; // Password cannot be null or empty
        }

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
}
