package com.agrosync.application.primaryports.mapper;

import com.agrosync.domain.model.GastoDomain;
import com.agrosync.application.primaryports.dto.GastoDTO;
import com.agrosync.application.secondaryports.entity.GastoEntity;
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
