package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID>, JpaSpecificationExecutor<AnimalEntity> {

    boolean existsByNumeroAnimalAndSuscripcion_Id(String numeroAnimal, UUID suscripcionId);

    boolean existsByNumeroAnimalAndIdNotAndSuscripcion_Id(String numeroAnimal, UUID id, UUID suscripcionId);

    Optional<AnimalEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

}
