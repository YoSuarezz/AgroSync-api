package com.sedikev.application.mapper;

import com.sedikev.application.domain.UsuarioDomain;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.domain.entity.UsuarioEntity;
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
