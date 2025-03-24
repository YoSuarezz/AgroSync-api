package com.sedikev.domain.service;

import com.sedikev.domain.model.AnimalDomain;

import java.util.List;

public interface AnimalService {

    AnimalDomain save(AnimalDomain animalDomain);

    AnimalDomain update(AnimalDomain animalDomain);

    AnimalDomain findById(String id);

    void deleteById(String id);

    List<AnimalDomain> findAll();

    List<AnimalDomain> findByLote(Long idLote);
}