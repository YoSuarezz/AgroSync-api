package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoteRepository extends JpaRepository<LoteEntity, UUID>, JpaSpecificationExecutor<LoteEntity> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM LoteEntity l WHERE l.contramarca = :contramarca AND FUNCTION('YEAR', l.fecha) = :year AND FUNCTION('WEEK', l.fecha) = :weekNumber AND l.suscripcion.id = :suscripcionId")
    boolean existsByContramarcaAndWeekAndYearAndSuscripcionId(String contramarca, int weekNumber, int year, UUID suscripcionId);

    Optional<LoteEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

}
