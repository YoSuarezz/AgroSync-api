package com.sedikev.application.mapper;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    // DOMAIN -> ENTITY
    @Mapping(source = "idLote", target = "lote.id")
    AnimalEntity toEntity(AnimalDomain domain);

    // ENTITY -> DOMAIN
    @Mapping(source = "lote.id", target = "idLote")
    AnimalDomain toDomain(AnimalEntity entity);

    // DOMAIN -> DTO
    AnimalDTO toDTO(AnimalDomain domain);

    // DTO -> DOMAIN
    AnimalDomain toDomain(AnimalDTO dto);
}
