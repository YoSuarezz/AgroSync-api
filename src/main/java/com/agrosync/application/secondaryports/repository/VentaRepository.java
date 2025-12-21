package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, UUID>, JpaSpecificationExecutor<VentaEntity> {

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    @EntityGraph(attributePaths = {"cliente", "animales", "cuentaCobrar", "cuentaCobrar.cliente"})
    Optional<VentaEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
