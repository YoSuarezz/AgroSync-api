package com.agrosync.application.primaryports.mapper.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.TipoUsuarioDTO;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;
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
