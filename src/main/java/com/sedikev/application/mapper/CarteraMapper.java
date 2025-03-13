package com.sedikev.application.mapper;

import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarteraMapper {

    CarteraMapper INSTANCE = Mappers.getMapper(CarteraMapper.class);

    CarteraDTO toDTO(CarteraDomain carteraDomain);
    CarteraDomain toDomain(CarteraDTO carteraDTO);
    CarteraEntity toEntity(CarteraDomain carteraDomain);
    CarteraDomain toDomain(CarteraEntity carteraEntity);
}
