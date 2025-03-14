package com.sedikev.application.mapper;

import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.dto.VentaDTO;
import com.sedikev.infrastructure.adapter.entity.VentaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    @Mapping(source = "id", target = "id")
    VentaDTO toDTO(VentaDomain ventaDomain);
    @Mapping(source = "id", target = "id")
    VentaDomain toDomain(VentaDTO ventaDTO);
    @Mapping(source = "id", target = "id")
    VentaEntity toEntity(VentaDomain ventaDomain);
    @Mapping(source = "id", target = "id")
    VentaDomain toDomain(VentaEntity ventaEntity);
}
