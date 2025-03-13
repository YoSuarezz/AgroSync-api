package com.sedikev.application.mapper;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {LoteMapper.class})
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    AnimalDTO toDTO(AnimalDomain animalDomain);
    AnimalDomain toDomain(AnimalDTO animalDTO);
    AnimalEntity toEntity(AnimalDomain animalDomain);
    @Mapping(source = "lote", target = "lote")
    AnimalDomain toDomain(AnimalEntity animalEntity);
}