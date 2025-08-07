package com.sedikev.application.primaryports.mapper;

import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.primaryports.dto.GastoDTO;
import com.sedikev.application.secondaryports.entity.GastoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {LoteMapper.class})
public interface GastoMapper {

    GastoDTO toDTO(GastoDomain domain);

    GastoDomain toDomain(GastoDTO dto);

    @Mapping(source = "lote.id",    target = "lote.id")
    GastoEntity toEntity(GastoDomain domain);

    @Mapping(source = "lote.id",    target = "lote.id")
    GastoDomain toDomain(GastoEntity entity);
}
