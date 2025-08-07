package com.sedikev.application.primaryports.service;

import com.sedikev.application.primaryports.mapper.AnimalMapper;
import com.sedikev.application.usecase.animal.*;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.secondaryports.repository.AnimalRepository;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.application.secondaryports.entity.AnimalEntity;
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

    private final AnimalMapper animalMapper;

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

    @Override
    public List<AnimalDomain> findByVenta(Long idVenta) {
        return animalRepository.findAll().stream()
                .map(animalMapper::toDomain)
                .filter(animalDomain -> animalDomain.getIdVenta().equals(idVenta))
                .collect(Collectors.toList());
    }

}