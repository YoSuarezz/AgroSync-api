package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, UUID>, JpaSpecificationExecutor<CompraEntity> {

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    Optional<CompraEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
