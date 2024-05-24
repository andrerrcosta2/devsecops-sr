package com.nobblecrafts.challenge.devsecopssr.domain.core.config;

import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainException;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignGlobalError implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign Client error: {}", response);
        if(response.status() == HttpStatus.NOT_FOUND.value()) {
            log.error("Error fetching data on server communication. Data not found");
            throw new DomainNotFoundException("Data not found");
        }
        log.error("Feign client error: {}, {}", methodKey, response);
        return new DomainException("Server Error");
    }
}