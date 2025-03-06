package com.sedikev.application.mapper;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.application.domain.AnimalDomain;
import com.sedikev.domain.entity.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    AnimalDTO animalDomainToAnimalDTO(AnimalDomain animalDomain);
    AnimalDomain animalDTOToAnimalDomain(AnimalDTO animalDTO);
    AnimalEntity animalDomainToAnimalEntity(AnimalDomain animalDomain);
    AnimalDomain animalEntityToAnimalDomain(AnimalEntity animalEntity);
}