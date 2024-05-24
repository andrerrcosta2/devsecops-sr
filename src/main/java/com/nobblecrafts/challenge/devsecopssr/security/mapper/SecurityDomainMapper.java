package com.nobblecrafts.challenge.devsecopssr.security.mapper;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.security.core.userdetails.AccountDetails;
import com.nobblecrafts.challenge.devsecopssr.security.model.SecurityAccountDTO;
import org.springframework.stereotype.Component;

@Component
public class SecurityDomainMapper {

    public SecurityAccountDTO securityAccountDTO(AccountEntity e) {
        return SecurityAccountDTO.builder()
                .principal(e.getUsername())
                .id(e.getId())
                .build();
    }

    public AccountDetails toUserDetails(AccountEntity e) {
        return new AccountDetails(e);
    }
}
