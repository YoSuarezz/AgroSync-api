package com.sedikev.application.service;

import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.application.usecase.animal.*;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalFacadeImpl implements AnimalService {

    private final CreateAnimalUseCase createAnimalUseCase;
    private final UpdateAnimalUseCase updateAnimalUseCase;
    private final DeleteAnimalUseCase deleteAnimalUseCase;
    private final GetAnimalByIdUseCase getAnimalByIdUseCase;
    private final GetAllAnimalsUseCase getAllAnimalsUseCase;
    private final GetAnimalsByLoteUseCase getAnimalsByLoteUseCase;

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public AnimalDomain save(AnimalDomain animalDomain) {
        return createAnimalUseCase.ejecutar(animalDomain);
    }

    @Override
    public AnimalDomain update(AnimalDomain animalDomain) {
        return updateAnimalUseCase.ejecutar(animalDomain);
    }

    @Override
    public AnimalDomain findById(Long id) {
        return getAnimalByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(Long id) {
        deleteAnimalUseCase.ejecutar(id);
    }

    @Override
    public List<AnimalDomain> findAll() {
        return getAllAnimalsUseCase.ejecutar(null);
    }

    @Override
    public List<AnimalDomain> findByLote(Long idLote) {
        return getAnimalsByLoteUseCase.ejecutar(idLote);
    }

    @Override
    @Transactional
    public void deleteByLote(Long idLote) {
        System.out.println("DEBUG: Iniciando eliminación para lote ID: " + idLote);

        List<AnimalEntity> animales = animalRepository.findByLoteId(idLote);

        System.out.println("DEBUG: Encontrados " + animales.size() + " animales para eliminar:");
        animales.forEach(a -> System.out.println("DEBUG: Animal ID=" + a.getId() +
                ", Detalles=" + a.toString()));

        animales.forEach(animalRepository::delete);

        System.out.println("DEBUG: Eliminación completada para lote ID: " + idLote);
    }

}
