package com.sedikev.application.primaryports.mapper;

import com.sedikev.application.primaryports.dto.VentaDTO;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.secondaryports.entity.VentaEntity;
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
