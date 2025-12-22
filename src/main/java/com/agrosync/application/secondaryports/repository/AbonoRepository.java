package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AbonoRepository extends JpaRepository<AbonoEntity, UUID>, JpaSpecificationExecutor<AbonoEntity> {

    @EntityGraph(attributePaths = {"cuentaPagar"})
    List<AbonoEntity> findByCuentaPagar_IdAndSuscripcion_Id(UUID cuentaPagarId, UUID suscripcionId);

    List<AbonoEntity> findBySuscripcion_Id(UUID suscripcionId);

    @EntityGraph(attributePaths = {"cuentaPagar"})
    Optional<AbonoEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    @EntityGraph(attributePaths = {"cuentaPagar"})
    Page<AbonoEntity> findAllBySuscripcion_Id(UUID suscripcionId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"cuentaPagar"})
    Page<AbonoEntity> findAll(Specification<AbonoEntity> spec, Pageable pageable);

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
