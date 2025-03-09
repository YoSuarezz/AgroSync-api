package com.sedikev.domain.service;

import com.sedikev.domain.model.AnimalDomain;

import java.util.List;

public interface AnimalService {

    AnimalDomain save(AnimalDomain animalDomain); // Guardar un animal (recibe y retorna un dominio)

    AnimalDomain findById(String id); // Buscar un animal por ID (retorna un dominio)

    void deleteById(String id); // Eliminar un animal por ID

    List<AnimalDomain> findAll(); // Obtener todos los animales (retorna una lista de dominios)

    List<AnimalDomain> findByLote(Long idLote); // Buscar animales por ID de lote (retorna una lista de dominios)
}