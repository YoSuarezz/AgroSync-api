package com.agrosync.application.primaryports.mapper.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.ActualizarUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.RegistrarNuevoUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface UsuarioDTOMapper {

    UsuarioDTOMapper INSTANCE = Mappers.getMapper(UsuarioDTOMapper.class);

    UsuarioDomain toDomain(RegistrarNuevoUsuarioDTO dto);

    UsuarioDomain toDomain(ActualizarUsuarioDTO dto);

    ObtenerUsuarioDTO toObtenerDTO(UsuarioDomain domain);

    List<ObtenerUsuarioDTO> toObtenerDTOCollection(List<UsuarioDomain> domainList);

    default Page<ObtenerUsuarioDTO> toObtenerDTOCollection(Page<UsuarioDomain> DomainPage) {
        List<ObtenerUsuarioDTO> dtoList = toObtenerDTOCollection(DomainPage.getContent());
        return new PageImpl<>(dtoList, DomainPage.getPageable(), DomainPage.getTotalElements());
    }

}
