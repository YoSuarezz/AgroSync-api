package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.domain.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @PostMapping(path = "animal")
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO animalDTO) {
        AnimalDomain animalDomain = animalMapper.toDomain(animalDTO);
        AnimalDomain animalSaved = animalService.save(animalDomain);
        AnimalDTO responseDTO = animalMapper.toDTO(animalSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "animal")
    public ResponseEntity<AnimalDTO> update(@RequestBody AnimalDTO animalDTO) {
        AnimalDomain animalDomain = animalMapper.toDomain(animalDTO);
        AnimalDomain animalSaved = animalService.save(animalDomain);
        AnimalDTO responseDTO = animalMapper.toDTO(animalSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "animal/{id}")
    public void delete(@PathVariable String id) {
        animalService.deleteById(id);
    }

    @GetMapping(path = "animal/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable String id) {
        AnimalDomain animalDomain = animalService.findById(id);
        if (animalDomain == null) {
            return ResponseEntity.notFound().build();
        }
        AnimalDTO responseDTO = animalMapper.toDTO(animalDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "animals")
    public ResponseEntity<List<AnimalDTO>> findAll() {
        List<AnimalDomain> animalDomains = animalService.findAll();
        List<AnimalDTO> responseDTOs = animalDomains.stream()
                .map(animalMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

}