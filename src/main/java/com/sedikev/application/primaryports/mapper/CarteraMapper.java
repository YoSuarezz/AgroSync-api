package com.sedikev.application.primaryports.mapper;

import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.application.primaryports.dto.CarteraDTO;
import com.sedikev.application.secondaryports.entity.CarteraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface CarteraMapper {

    CarteraDTO toDTO(CarteraDomain domain);

    CarteraDomain toDomain(CarteraDTO dto);

    @Mapping(source = "usuario.id", target = "usuario.id")
    CarteraEntity toEntity(CarteraDomain domain);

    @Mapping(source = "usuario.id", target = "usuario.id")
    CarteraDomain toDomain(CarteraEntity entity);
}
