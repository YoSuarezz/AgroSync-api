package com.sedikev.application.mapper;

import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(UsuarioDomain domain);

    UsuarioDomain toDomain(UsuarioDTO dto);

    UsuarioEntity toEntity(UsuarioDomain domain);

    UsuarioDomain toDomain(UsuarioEntity entity);
}
