package com.sedikev.application.mapper;

import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.dto.PagoDTO;
import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PagoMapper {

    PagoMapper INSTANCE = Mappers.getMapper(PagoMapper.class);

    @Mapping(source = "id", target = "id")
    PagoDTO toDTO(PagoDomain pagoDomain);
    @Mapping(source = "id", target = "id")
    PagoDomain toDomain(PagoDTO pagoDTO);
    @Mapping(source = "id", target = "id")
    PagoEntity toEntity(PagoDomain pagoDomain);
    @Mapping(source = "id", target = "id")
    PagoDomain toDomain(PagoEntity pagoEntity);
}
