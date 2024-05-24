package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountResponse;

public interface AccountService {
    RegisterAccountResponse register(RegisterAccountRequest request);
}
