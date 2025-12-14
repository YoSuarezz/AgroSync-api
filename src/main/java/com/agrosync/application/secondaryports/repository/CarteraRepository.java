package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarteraRepository extends JpaRepository<CarteraEntity, UUID>, JpaSpecificationExecutor<CarteraEntity> {

    Optional<CarteraEntity> findByUsuario_IdAndSuscripcion_Id(UUID usuarioId, UUID suscripcionId);
}
