package com.sedikev.application.mapper;

import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.dto.PagoDTO;
import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PagoMapper {

    PagoMapper INSTANCE = Mappers.getMapper(PagoMapper.class);

    PagoDTO toDTO(PagoDomain pagoDomain);
    PagoDomain toDomain(PagoDTO pagoDTO);
    PagoEntity toEntity(PagoDomain pagoDomain);
    PagoDomain toDomain(PagoEntity pagoEntity);
}
