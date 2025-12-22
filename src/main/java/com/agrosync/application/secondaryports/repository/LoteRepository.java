package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoteRepository extends JpaRepository<LoteEntity, UUID>, JpaSpecificationExecutor<LoteEntity> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM LoteEntity l WHERE l.contramarca = :contramarca AND EXTRACT(YEAR FROM l.fecha) = :year AND EXTRACT(WEEK FROM l.fecha) = :weekNumber AND l.suscripcion.id = :suscripcionId AND l.compra.estado != 'ANULADA'")
    boolean existsByContramarcaAndWeekAndYearAndSuscripcionIdAndCompraNotAnulada(String contramarca, int weekNumber, int year, UUID suscripcionId);

    @EntityGraph(attributePaths = {"compra", "animales"})
    Optional<LoteEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    @Override
    @EntityGraph(attributePaths = {"compra", "animales"})
    Page<LoteEntity> findAll(Specification<LoteEntity> spec, Pageable pageable);

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
