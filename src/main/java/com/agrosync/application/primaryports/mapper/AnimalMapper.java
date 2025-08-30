package com.agrosync.application.primaryports.mapper;

import com.agrosync.application.primaryports.dto.AnimalDTO;
import com.agrosync.domain.model.AnimalDomain;
import com.agrosync.application.secondaryports.entity.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    // DOMAIN -> ENTITY
    @Mapping(source = "idLote",   target = "lote.id")
    @Mapping(source = "idVenta",  target = "venta.id")
    AnimalEntity toEntity(AnimalDomain domain);

    // ENTITY -> DOMAIN
    @Mapping(source = "lote.id",   target = "idLote")
    @Mapping(source = "venta.id",  target = "idVenta")
    AnimalDomain toDomain(AnimalEntity entity);

    // DOMAIN -> DTO  (nombres coinciden: id, idLote, idVenta, peso, sexo, slot, precioKiloCompra, precioKiloVenta)
    AnimalDTO toDTO(AnimalDomain domain);

    // DTO -> DOMAIN
    AnimalDomain toDomain(AnimalDTO dto);
}
