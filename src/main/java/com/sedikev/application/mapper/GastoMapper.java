package com.sedikev.application.mapper;

import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.dto.GastoDTO;
import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {GastoMapper.class})
public interface GastoMapper {

    GastoMapper INSTANCE = Mappers.getMapper(GastoMapper.class);

    @Mapping(source = "id", target = "id")
    GastoDTO toDTO(GastoDomain gastoDomain);
    @Mapping(source = "id", target = "id")
    GastoDomain toDomain(GastoDTO gastoDTO);
    @Mapping(source = "id", target = "id")
    GastoEntity toEntity(GastoDomain gastoDomain);
    @Mapping(source = "id", target = "id")
    GastoDomain toDomain(GastoEntity gastoEntity);
}

