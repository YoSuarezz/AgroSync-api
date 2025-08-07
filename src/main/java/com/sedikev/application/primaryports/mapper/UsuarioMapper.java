package com.sedikev.application.primaryports.mapper;

import com.sedikev.domain.usuarios.UsuarioDomain;
import com.sedikev.application.primaryports.dto.usuarios.UsuarioDTO;
import com.sedikev.application.secondaryports.entity.usuarios.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "id",   target = "id")
    UsuarioDTO toDTO(UsuarioDomain domain);
    @Mapping(source = "id",   target = "id")
    UsuarioDomain toDomain(UsuarioDTO dto);
    @Mapping(source = "id",   target = "id")
    UsuarioEntity toEntity(UsuarioDomain domain);
    @Mapping(source = "id",   target = "id")
    UsuarioDomain toDomain(UsuarioEntity entity);
}
