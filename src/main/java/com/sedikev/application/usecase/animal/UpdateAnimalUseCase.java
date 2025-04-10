package com.sedikev.application.usecase.animal;

import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UpdateAnimalUseCase implements UseCaseWithReturn<AnimalDomain, AnimalDomain> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public AnimalDomain ejecutar(AnimalDomain animalDomain) {

        if (animalDomain.getPeso() == null || animalDomain.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El peso debe ser un número positivo");
        }

        if (!animalRepository.existsById(animalDomain.getId())) {
            throw new BusinessSedikevException("El animal no existe");
        }

        if (!Objects.equals(animalDomain.getSexo(), "macho") && !Objects.equals(animalDomain.getSexo(), "hembra")) {
            throw new BusinessSedikevException("El sexo debe ser macho o hembra");
        }

        if (animalDomain.getIdLote() == null) {
            throw new BusinessSedikevException("El animal debe estar asociado a un lote");
        }

        if (animalDomain.getNum_lote() == null || animalDomain.getNum_lote() <= 0 || animalDomain.getNum_lote() > 25) {
            throw new BusinessSedikevException("El animal debe estar asociado a un slot entre 1 y 25");
        }

        AnimalEntity animalEntity = animalMapper.toEntity(animalDomain);
        AnimalEntity animalUpdated = animalRepository.save(animalEntity);
        return animalMapper.toDomain(animalUpdated);
    }
}