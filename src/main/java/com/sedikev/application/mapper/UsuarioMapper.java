package com.sedikev.application.mapper;

import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
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
