package com.agrosync.application.primaryports.mapper;

import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
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
