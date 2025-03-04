package com.sedikev.domain.service;


import com.sedikev.domain.entity.AnimalEntity;

import java.util.List;

public interface AnimalService {

    AnimalEntity save(AnimalEntity animalEntity);

    AnimalEntity findById(String id);

    void deleteById(String id);

    List<AnimalEntity> findAll();

    List<AnimalEntity> findByLote(Long id_lote);
}
