package com.nobblecrafts.challenge.devsecopssr.security.exception;

public class SecurityDomainException extends RuntimeException {
    public SecurityDomainException(String message) {
        super(message);
    }

    public SecurityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
