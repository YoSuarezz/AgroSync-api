package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AbonoRepository extends JpaRepository<AbonoEntity, UUID> {

    List<AbonoEntity> findByCuentaPagar_IdAndSuscripcion_Id(UUID cuentaPagarId, UUID suscripcionId);

    Optional<AbonoEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
