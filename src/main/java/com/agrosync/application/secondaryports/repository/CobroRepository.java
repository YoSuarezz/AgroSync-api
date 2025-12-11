package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CobroRepository extends JpaRepository<CobroEntity, UUID> {

    List<CobroEntity> findByCuentaCobrar_IdAndSuscripcion_Id(UUID cuentaCobrarId, UUID suscripcionId);

    Optional<CobroEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
