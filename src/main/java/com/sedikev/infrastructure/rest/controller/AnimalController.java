package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.domain.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalMapper animalMapper;

    @PostMapping(path = "animal")
    public AnimalDTO create(@RequestBody AnimalDTO animalDTO) {
        AnimalDomain animalDomain = animalMapper.toDomain(animalDTO);
        AnimalDomain animalSaved = animalService.save(animalDomain);
        return animalMapper.toDTO(animalSaved);
    }

    @PutMapping(path = "animal")
    public AnimalDTO update(@RequestBody AnimalDTO animalDTO) {
        AnimalDomain animalDomain = animalMapper.toDomain(animalDTO);
        AnimalDomain animalSaved = animalService.save(animalDomain);
        return animalMapper.toDTO(animalSaved);
    }

    @DeleteMapping(path = "animal/{id}")
    public void delete(@PathVariable String id) {
        animalService.deleteById(id);
    }

    @GetMapping(path = "animal/{id}")
    public AnimalDTO findById(@PathVariable String id) {
        return animalMapper.toDTO(animalService.findById(id));
    }

    @GetMapping(path = "animals")
    public List<AnimalDTO> findAll(){
        return animalService.findAll().stream()
                .map(animalMapper::toDTO)
                .collect(Collectors.toList());
    }

}