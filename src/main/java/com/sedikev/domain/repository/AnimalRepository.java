package com.sedikev.domain.repository;

import com.sedikev.domain.entity.AnimalEntity;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    AnimalEntity save(AnimalEntity animalEntity);

    Optional<AnimalEntity> findById(String id);

    void deleteById(String id);

    List<AnimalEntity> findAll();
}
