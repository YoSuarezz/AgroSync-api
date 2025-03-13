package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, String> {

    AnimalEntity save(AnimalEntity animalEntity);

    Optional<AnimalEntity> findById(String id);

    void deleteById(String id);

    List<AnimalEntity> findAll();
}
