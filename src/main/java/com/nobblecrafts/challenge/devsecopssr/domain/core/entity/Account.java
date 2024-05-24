package com.nobblecrafts.challenge.devsecopssr.domain.core.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {
    private Long id;
    private String username;
    private String password;
}
