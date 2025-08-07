package com.sedikev.application.primaryports.mapper.usuarios;

import com.sedikev.application.primaryports.dto.usuarios.TipoUsuarioDTO;
import com.sedikev.domain.usuarios.TipoUsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoUsuarioDTOMapper {

    TipoUsuarioDTOMapper INSTANCE = Mappers.getMapper(TipoUsuarioDTOMapper.class);

    TipoUsuarioDomain toDomain(TipoUsuarioDTO dto);

    TipoUsuarioDTO toDTO(TipoUsuarioDomain domain);

    List<TipoUsuarioDTO> toDTOCollection(List<TipoUsuarioDomain> domainList);
}
