package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Animal save(Animal animal);

    Optional<Animal> findById(String id);

    void deleteById(String id);

    List<Animal> findAll();
}
