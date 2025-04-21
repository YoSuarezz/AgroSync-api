package com.sedikev.application.mapper;

import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.dto.GastoDTO;
import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {UsuarioMapper.class, LoteMapper.class})
public interface GastoMapper {

    GastoDTO toDTO(GastoDomain domain);

    GastoDomain toDomain(GastoDTO dto);

    @Mapping(source = "lote.id",    target = "lote.id")
    @Mapping(source = "usuario.id", target = "usuario.id")
    GastoEntity toEntity(GastoDomain domain);

    @Mapping(source = "lote.id",    target = "lote.id")
    @Mapping(source = "usuario.id", target = "usuario.id")
    GastoDomain toDomain(GastoEntity entity);
}
