package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SuscripcionRepository extends JpaRepository<SuscripcionEntity, UUID>, JpaSpecificationExecutor<SuscripcionEntity> {
}
