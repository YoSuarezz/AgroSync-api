package com.agrosync.application.primaryports.mapper.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface UsuarioDTOMapper {

    UsuarioDTOMapper INSTANCE = Mappers.getMapper(UsuarioDTOMapper.class);

    UsuarioDomain toDomain(UsuarioDTO dto);

    UsuarioDTO toDTO(UsuarioDomain domain);

    List<UsuarioDTO> toDTOCollection(List<UsuarioDomain> domainList);

    default Page<UsuarioDTO> toDTOCollection(Page<UsuarioDomain> DomainPage) {
        List<UsuarioDTO> dtoList = toDTOCollection(DomainPage.getContent());
        return new PageImpl<>(dtoList, DomainPage.getPageable(), DomainPage.getTotalElements());
    }

}
