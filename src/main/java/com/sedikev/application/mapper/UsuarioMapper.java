package com.sedikev.application.mapper;

import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDTO(UsuarioDomain usuarioDomain);
    UsuarioDomain toDomain(UsuarioDTO usuarioDTO);
    UsuarioEntity toEntity(UsuarioDomain usuarioDomain);
    UsuarioDomain toDomain(UsuarioEntity usuarioEntity);
}
