package com.sedikev.application.service;

import com.sedikev.application.usecase.animal.*;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.domain.service.AnimalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final CreateAnimalUseCase createAnimalUseCase;
    private final UpdateAnimalUseCase updateAnimalUseCase;
    private final DeleteAnimalUseCase deleteAnimalUseCase;
    private final GetAnimalByIdUseCase getAnimalByIdUseCase;
    private final GetAllAnimalsUseCase getAllAnimalsUseCase;
    private final GetAnimalsByLoteUseCase getAnimalsByLoteUseCase;

    @Override
    public AnimalDomain save(AnimalDomain animalDomain) {
        return createAnimalUseCase.ejecutar(animalDomain);
    }

    @Override
    public AnimalDomain update(AnimalDomain animalDomain) {
        return updateAnimalUseCase.ejecutar(animalDomain);
    }

    @Override
    public AnimalDomain findById(String id) {
        return getAnimalByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(String id) {
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
}
