package com.sedikev.application.usecase.animal;

import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.application.usecase.UseCaseWithReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAnimalsByLoteUseCase implements UseCaseWithReturn<Long, List<AnimalDomain>> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public List<AnimalDomain> ejecutar(Long idLote) {
        // Obtener animales por lote
        return animalRepository.findAll().stream()
                .filter(animalEntity -> idLote.equals(animalEntity.getLote().getId()))
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }
}