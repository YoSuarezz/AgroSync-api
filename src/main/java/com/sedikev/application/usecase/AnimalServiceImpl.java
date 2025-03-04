package com.sedikev.application.usecase;

import com.sedikev.domain.entity.AnimalEntity;
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
    public AnimalEntity save(AnimalEntity animalEntity) {
        return animalRepository.save(animalEntity);
    }

    @Transactional(readOnly = true)
    public AnimalEntity findById(String id) {
        return animalRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        animalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AnimalEntity> findAll() {
        return (List<AnimalEntity>) animalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AnimalEntity> findByLote(Long id_lote) {
        List<AnimalEntity> lista_animalEntity = new ArrayList<>();
        for (AnimalEntity animalEntity : animalRepository.findAll()) {
            if (Objects.equals(animalEntity.getLoteEntity().getId(), id_lote)){
                lista_animalEntity.add(animalEntity);
            }
        }
        return lista_animalEntity;
    }
}
