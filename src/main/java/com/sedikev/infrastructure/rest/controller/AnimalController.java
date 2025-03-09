package com.sedikev.infrastructure.rest.controller;

import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.domain.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @Autowired
    public AnimalController(AnimalService animalService, AnimalMapper animalMapper) {
        this.animalService = animalService;
        this.animalMapper = animalMapper;
    }

    public AnimalDomain crearAnimal(AnimalDomain animalDomain) {
        return animalService.save(animalDomain);
    }

    public AnimalDomain obtenerAnimalPorId(String id) {
        return animalService.findById(id);
    }

    public List<AnimalDomain> obtenerTodosLosAnimales() {
        return animalService.findAll();
    }

    public List<AnimalDomain> obtenerAnimalesPorLote(Long idLote) {
        return animalService.findByLote(idLote);
    }

    public void eliminarAnimal(String id) {
        animalService.deleteById(id);
    }
}