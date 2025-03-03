package com.sedikev.domain.service;


import com.sedikev.domain.entity.Animal;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal);

    Animal findById(String id);

    void deleteById(String id);

    List<Animal> findAll();

    List<Animal> findByLote(Long id_lote);
}
