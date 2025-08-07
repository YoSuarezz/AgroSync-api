package com.sedikev.application.mapper;

import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.dto.PagoDTO;
import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {VentaMapper.class})
public interface PagoMapper {

    PagoDTO toDTO(PagoDomain domain);

    PagoDomain toDomain(PagoDTO dto);

    @Mapping(source = "venta.id",   target = "venta.id")
    PagoEntity toEntity(PagoDomain domain);

    @Mapping(source = "venta.id",   target = "venta.id")
    PagoDomain toDomain(PagoEntity entity);
}
