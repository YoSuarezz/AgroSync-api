package com.sedikev.application.mapper;

import com.sedikev.application.domain.VentaDomain;
import com.sedikev.application.dto.VentaDTO;
import com.sedikev.domain.entity.VentaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    VentaDTO toDTO(VentaDomain ventaDomain);
    VentaDomain toDomain(VentaDTO ventaDTO);
    VentaEntity toEntity(VentaDomain ventaDomain);
    VentaDomain toDomain(VentaEntity ventaEntity);
}
