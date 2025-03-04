package com.sedikev.application.mapper;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.domain.entity.Usuario;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);
    Usuario toEntity(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);
    List<Usuario> toEntityList(List<UsuarioDTO> usuarioDTOs);
}
