package com.agrosync.domain.service;

import com.agrosync.domain.model.AnimalDomain;

import java.util.List;

public interface AnimalService {

    AnimalDomain save(AnimalDomain animalDomain);

    AnimalDomain update(AnimalDomain animalDomain);

    AnimalDomain findById(Long id);

    void deleteById(Long id);

    List<AnimalDomain> findAll();

    List<AnimalDomain> findByLote(Long idLote);

    public void deleteByLote(Long idLote);

    public List<AnimalDomain> findByVenta(Long idVenta);

}