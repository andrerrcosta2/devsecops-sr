package com.nobblecrafts.challenge.devsecopssr.security.validation.pattern;

public class DomainSecurityPattern {

    public static final String PASSWORD =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$";

    public static final String ONLY_LETTERS = "^[a-zA-Z]+$";

    public static final String ONLY_NUMBERS = "^[0-9]+$";

    public static final String ALPHANUMERIC = "^[a-zA-Z0-9]+$";

    public static final String SPACE_PLUS_ALPHANUMERIC = "^[a-zA-Z0-9 ]+$";

    public static final String UNDERSCORE_PLUS_ALPHANUMERIC = "^\\w+$";
}
