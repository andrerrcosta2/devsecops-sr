package com.nobblecrafts.challenge.devsecopssr.security.validation.constraint;

import com.nobblecrafts.challenge.devsecopssr.security.validation.DomainValidAccountPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DomainAccountPasswordValidator implements ConstraintValidator<DomainValidAccountPassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}
