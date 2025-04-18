package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, String> {

    AnimalEntity save(AnimalEntity animalEntity);

    Optional<AnimalEntity> findById(String id);

    void deleteById(String id);

    void deleteByLoteId(Long loteId);

    List<AnimalEntity> findByLoteId(Long loteId);

    List<AnimalEntity> findAll();

}
