package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
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
public interface CobroRepository extends JpaRepository<CobroEntity, UUID>, JpaSpecificationExecutor<CobroEntity> {

    @EntityGraph(attributePaths = {"cuentaCobrar"})
    List<CobroEntity> findByCuentaCobrar_IdAndSuscripcion_Id(UUID cuentaCobrarId, UUID suscripcionId);

    @EntityGraph(attributePaths = {"cuentaCobrar"})
    Optional<CobroEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    @EntityGraph(attributePaths = {"cuentaCobrar"})
    Page<CobroEntity> findAllBySuscripcion_Id(UUID suscripcionId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"cuentaCobrar"})
    Page<CobroEntity> findAll(Specification<CobroEntity> spec, Pageable pageable);

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    List<CobroEntity> findBySuscripcion_Id(UUID suscripcionId);
}
