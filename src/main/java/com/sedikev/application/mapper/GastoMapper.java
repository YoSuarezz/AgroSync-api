package com.sedikev.application.mapper;

import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.dto.GastoDTO;
import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GastoMapper {

    GastoMapper INSTANCE = Mappers.getMapper(GastoMapper.class);

    GastoDTO toDTO(GastoDomain gastoDomain);
    GastoDomain toDomain(GastoDTO gastoDTO);
    GastoEntity toEntity(GastoDomain gastoDomain);
    GastoDomain toDomain(GastoEntity gastoEntity);
}

