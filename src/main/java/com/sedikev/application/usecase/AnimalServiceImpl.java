package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Animal;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.domain.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private final AnimalRepository animalRepository;

    @Transactional
    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Transactional(readOnly = true)
    public Animal findById(String id) {
        return animalRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        animalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Animal> findAll() {
        return (List<Animal>) animalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Animal> findByLote(Long id_lote) {
        List<Animal> lista_animal = new ArrayList<>();
        for (Animal animal: animalRepository.findAll()) {
            if (Objects.equals(animal.getLote().getId(), id_lote)){
                lista_animal.add(animal);
            }
        }
        return lista_animal;
    }
}
