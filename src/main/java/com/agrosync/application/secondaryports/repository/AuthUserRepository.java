package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.auth.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserEntity, UUID> {

    Optional<AuthUserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
