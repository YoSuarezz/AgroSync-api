package com.sedikev.application.mapper;

import com.sedikev.application.dto.VentaDTO;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.infrastructure.adapter.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AnimalMapper.class, UsuarioMapper.class}
)
public interface VentaMapper {

    // DOMAIN -> ENTITY
    @Mapping(source = "precioVenta", target = "precioVenta")
    @Mapping(source = "animales",    target = "animales")
    VentaEntity toEntity(VentaDomain domain);

    // ENTITY -> DOMAIN
    @Mapping(source = "precioVenta", target = "precioVenta")
    @Mapping(source = "animales",    target = "animales")
    VentaDomain toDomain(VentaEntity entity);

    // DOMAIN -> DTO
    VentaDTO toDTO(VentaDomain domain);

    // DTO -> DOMAIN
    VentaDomain toDomain(VentaDTO dto);
}
