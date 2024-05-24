package com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.repository;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityJpaRepository extends JpaRepository<UserActivityEntity, Long> {
}
