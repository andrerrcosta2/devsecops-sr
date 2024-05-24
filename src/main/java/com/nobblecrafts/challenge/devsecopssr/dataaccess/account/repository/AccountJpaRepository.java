package com.nobblecrafts.challenge.devsecopssr.dataaccess.account.repository;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
