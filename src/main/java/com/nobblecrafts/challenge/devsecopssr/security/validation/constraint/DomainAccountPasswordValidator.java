package com.nobblecrafts.challenge.devsecopssr.security.validation.constraint;

import com.nobblecrafts.challenge.devsecopssr.security.validation.DomainValidAccountPassword;
import com.nobblecrafts.challenge.devsecopssr.security.validation.pattern.DomainSecurityPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DomainAccountPasswordValidator implements ConstraintValidator<DomainValidAccountPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return Pattern.matches(DomainSecurityPattern.PASSWORD, password);
    }
}
