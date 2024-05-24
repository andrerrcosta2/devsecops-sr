package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginResponse;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountResponse;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthService {
    LoginResponse login(LoginRequest request, HttpServletResponse response);

}
