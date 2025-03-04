package com.sedikev.application.mapper;

import com.sedikev.application.domain.CarteraDomain;
import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.domain.entity.CarteraEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarteraMapper {

    CarteraMapper INSTANCE = Mappers.getMapper(CarteraMapper.class);

    CarteraDTO toDTO(CarteraDomain carteraDomain);
    CarteraDomain toDomain(CarteraDTO carteraDTO);
    CarteraEntity toEntity(CarteraDomain carteraDomain);
    CarteraDomain toDomain(CarteraEntity carteraEntity);
}
