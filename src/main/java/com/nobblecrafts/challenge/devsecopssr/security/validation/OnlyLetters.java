package com.nobblecrafts.challenge.devsecopssr.security.validation;

import com.nobblecrafts.challenge.devsecopssr.security.validation.constraint.DomainAccountPasswordValidator;
import com.nobblecrafts.challenge.devsecopssr.security.validation.constraint.OnlyLettersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OnlyLettersValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLetters {
    String message() default "Only letters allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
