package com.sedikev.application.mapper;

import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
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
