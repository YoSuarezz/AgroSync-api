package com.sedikev.application.primaryports.mapper.usuarios;

import com.sedikev.application.primaryports.dto.usuarios.UsuarioDTO;
import com.sedikev.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UsuarioDTOMapper {

    UsuarioDTOMapper INSTANCE = Mappers.getMapper(UsuarioDTOMapper.class);

    UsuarioDomain toDomain(UsuarioDTO dto);

    UsuarioDTO toDTO(UsuarioDomain domain);

    List<UsuarioDTO> toDTOCollection(List<UsuarioDomain> domainList);


}
