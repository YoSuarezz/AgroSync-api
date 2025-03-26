package com.sedikev.application.mapper;

import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarteraMapper {

    @Mapping(source = "id", target = "id")
    CarteraDTO toDTO(CarteraDomain carteraDomain);
    @Mapping(source = "id", target = "id")
    CarteraDomain toDomain(CarteraDTO carteraDTO);
    @Mapping(source = "id", target = "id")
    CarteraEntity toEntity(CarteraDomain carteraDomain);
    @Mapping(source = "id", target = "id")
    CarteraDomain toDomain(CarteraEntity carteraEntity);
}
