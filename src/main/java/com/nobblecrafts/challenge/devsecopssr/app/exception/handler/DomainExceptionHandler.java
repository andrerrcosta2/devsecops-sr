package com.nobblecrafts.challenge.devsecopssr.app.exception.handler;

import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainException;
import com.nobblecrafts.challenge.devsecopssr.security.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class DomainExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {DomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(DomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage()).build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleAuthenticationException(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.sendRedirect(request.getContextPath() + "/");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        response.sendRedirect(request.getContextPath() + "/");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleUsernameNotFoutException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
