package com.nobblecrafts.challenge.devsecopssr.security.validation;

import com.nobblecrafts.challenge.devsecopssr.security.validation.constraint.AlphanumericValidator;
import com.nobblecrafts.challenge.devsecopssr.security.validation.constraint.OnlyLettersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AlphanumericValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Alphanumeric {
    String message() default "Only letters and numbers allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
