package com.nobblecrafts.challenge.devsecopssr.security.decoder;

import com.nobblecrafts.challenge.devsecopssr.security.exception.TokenDecodingException;

public interface TokenDecoder<T> {
    String extractSubject(String token) throws TokenDecodingException;
    T decode(String token) throws TokenDecodingException;
}
