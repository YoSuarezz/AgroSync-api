package com.sedikev.application.mapper;

import com.sedikev.application.dto.LoteDTO;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
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
