package com.sedikev.application.mapper;

import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "id", target = "id")
    UsuarioDTO toDTO(UsuarioDomain usuarioDomain);
    @Mapping(source = "id", target = "id")
    UsuarioDomain toDomain(UsuarioDTO usuarioDTO);
    @Mapping(source = "id", target = "id")
    UsuarioEntity toEntity(UsuarioDomain usuarioDomain);
    @Mapping(source = "id", target = "id")
    UsuarioDomain toDomain(UsuarioEntity usuarioEntity);
}
