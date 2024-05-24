package com.nobblecrafts.challenge.devsecopssr.security.exception;

public class TokenDecodingException extends RuntimeException {
    public TokenDecodingException(String message) {
        super(message);
    }

    public TokenDecodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
