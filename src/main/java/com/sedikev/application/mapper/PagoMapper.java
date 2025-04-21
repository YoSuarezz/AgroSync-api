package com.sedikev.application.mapper;

import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.dto.PagoDTO;
import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {UsuarioMapper.class, VentaMapper.class})
public interface PagoMapper {

    PagoDTO toDTO(PagoDomain domain);

    PagoDomain toDomain(PagoDTO dto);

    @Mapping(source = "venta.id",   target = "venta.id")
    @Mapping(source = "usuario.id", target = "usuario.id")
    PagoEntity toEntity(PagoDomain domain);

    @Mapping(source = "venta.id",   target = "venta.id")
    @Mapping(source = "usuario.id", target = "usuario.id")
    PagoDomain toDomain(PagoEntity entity);
}
