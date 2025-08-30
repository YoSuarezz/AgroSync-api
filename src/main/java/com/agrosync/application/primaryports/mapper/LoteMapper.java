package com.agrosync.application.primaryports.mapper;

import com.agrosync.application.primaryports.dto.LoteDTO;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AnimalMapper.class}
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
