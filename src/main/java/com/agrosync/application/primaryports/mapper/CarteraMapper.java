package com.agrosync.application.primaryports.mapper;

import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.application.primaryports.dto.CarteraDTO;
import com.agrosync.application.secondaryports.entity.CarteraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarteraMapper {

    CarteraDTO toDTO(CarteraDomain domain);

    CarteraDomain toDomain(CarteraDTO dto);

    @Mapping(source = "usuario.id", target = "usuario.id")
    CarteraEntity toEntity(CarteraDomain domain);

    @Mapping(source = "usuario.id", target = "usuario.id")
    CarteraDomain toDomain(CarteraEntity entity);
}
