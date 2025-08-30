package com.agrosync.application.primaryports.mapper;

import com.agrosync.domain.model.PagoDomain;
import com.agrosync.application.primaryports.dto.PagoDTO;
import com.agrosync.application.secondaryports.entity.PagoEntity;
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
