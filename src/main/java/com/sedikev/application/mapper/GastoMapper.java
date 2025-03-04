package com.sedikev.application.mapper;

import com.sedikev.application.domain.GastoDomain;
import com.sedikev.application.dto.GastoDTO;
import com.sedikev.domain.entity.GastoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GastoMapper {

    GastoMapper INSTANCE = Mappers.getMapper(GastoMapper.class);

    GastoDTO toDTO(GastoDomain gastoDomain);
    GastoDomain toDomain(GastoDTO gastoDTO);
    GastoEntity toEntity(GastoDomain gastoDomain);
    GastoDomain toDomain(GastoEntity gastoEntity);
}

