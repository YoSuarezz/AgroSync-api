package com.sedikev.application.usecase.animal;

import com.sedikev.application.primaryports.mapper.AnimalMapper;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.secondaryports.repository.AnimalRepository;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.application.secondaryports.entity.AnimalEntity;
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
        List<AnimalEntity> animales = animalRepository.findByLoteId(idLote);
        return animales.stream()
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }
}