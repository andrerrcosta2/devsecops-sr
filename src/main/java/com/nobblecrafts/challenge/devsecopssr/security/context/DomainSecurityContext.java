package com.nobblecrafts.challenge.devsecopssr.security.context;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.repository.AccountJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.security.mapper.SecurityDomainMapper;
import com.nobblecrafts.challenge.devsecopssr.security.model.SecurityAccountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainSecurityContext {
    private final AccountJpaRepository accountRepository;
    private final SecurityDomainMapper mapper;

    @Transactional(readOnly = true)
    public SecurityAccountDTO requireCurrentSecurityAccountDTO() {
        Optional<String> username = getCurrentUserLogin();
        if (username.isEmpty()) {
            log.error("System error. Current user couldn't not be found");
            throw new AccessDeniedException("Unauthorized");
        }

        Optional<AccountEntity> account = accountRepository.findByUsername(username.get());
        log.info("requireCurrentSecurityAccountDTO: AllAccounts {}", accountRepository.findAll());
        if (username.isEmpty()) {
            log.error("System error. Current user couldn't not be found");
            throw new AccessDeniedException("Unauthorized");
        }
        log.info("\n\nrequireCurrentSecurityAccountDTO: {}, {}\n\n", username, account);
        return account.map(mapper::securityAccountDTO).get();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<SecurityAccountDTO> getCurrentSecurityAccountDTO() {
        Optional<String> username = getCurrentUserLogin();
        return username.flatMap(s -> accountRepository.findByUsername(s)
                .map(mapper::securityAccountDTO));
    }

    @Transactional(readOnly = true)
    public Optional<AccountEntity> getCurrentAccountEntity() {
        Optional<String> username = getCurrentAccountUsername();
        if (username.isPresent()) {
            return accountRepository.findByUsername(username.get());
        } else {
            log.error("System error. Current user couldn't not be found");
            throw new AccessDeniedException("Unauthorized");
        }
    }

    public Optional<String> getCurrentAccountUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(auth.getName());
    }

    public String requireCurrentAccountUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || StringUtils.isEmpty(auth.getName())) {
            throw new AccessDeniedException("Unauthorized");
        }
        return auth.getName();
    }

    @Transactional(readOnly = true)
    public Optional<UserDetails> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            String username = authentication.getName();
            Optional<AccountEntity> optional = accountRepository.findByUsername(username);
            if (optional.isPresent()) {
                AccountEntity account = optional.get();
                return Optional.of(mapper.toUserDetails(account));
            }
        }
        return Optional.empty();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }

    private Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwtToken) {
            return jwtToken.getSubject();
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else if (authentication.getPrincipal() instanceof String principal) {
            return principal;
        }
        return null;
    }
}
