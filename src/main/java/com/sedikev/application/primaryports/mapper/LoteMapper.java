package com.sedikev.application.primaryports.mapper;

import com.sedikev.application.primaryports.dto.LoteDTO;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.application.secondaryports.entity.LoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AnimalMapper.class, UsuarioMapper.class}
)
public interface LoteMapper {

    // DOMAIN -> ENTITY
    @Mapping(source = "precioTotal", target = "precioTotal")
    @Mapping(source = "animales",    target = "animales")
    LoteEntity toEntity(LoteDomain domain);

    // ENTITY -> DOMAIN
    @Mapping(source = "precioTotal", target = "precioTotal")
    @Mapping(source = "animales",    target = "animales")
    LoteDomain toDomain(LoteEntity entity);

    // DOMAIN -> DTO
    LoteDTO toDTO(LoteDomain domain);

    // DTO -> DOMAIN
    LoteDomain toDomain(LoteDTO dto);
}
