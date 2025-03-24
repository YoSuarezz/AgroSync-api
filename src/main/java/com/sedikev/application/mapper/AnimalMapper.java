package com.sedikev.application.mapper;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {LoteMapper.class})
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    @Mapping(source = "id", target = "id")
    AnimalDTO toDTO(AnimalDomain animalDomain);
    @Mapping(source = "id", target = "id")
    AnimalDomain toDomain(AnimalDTO animalDTO);
    @Mapping(source = "id", target = "id")
    AnimalEntity toEntity(AnimalDomain animalDomain);
    @Mapping(source = "id", target = "id")
    AnimalDomain toDomain(AnimalEntity animalEntity);
}