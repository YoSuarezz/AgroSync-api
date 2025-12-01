package com.agrosync.application.primaryports.mapper.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface UsuarioDTOMapper {

    UsuarioDTOMapper INSTANCE = Mappers.getMapper(UsuarioDTOMapper.class);

    UsuarioDomain toDomain(RegiserNewUserDTO dto);

    RegiserNewUserDTO toDTO(UsuarioDomain domain);

    List<RegiserNewUserDTO> toDTOCollection(List<UsuarioDomain> domainList);

    default Page<RegiserNewUserDTO> toDTOCollection(Page<UsuarioDomain> DomainPage) {
        List<RegiserNewUserDTO> dtoList = toDTOCollection(DomainPage.getContent());
        return new PageImpl<>(dtoList, DomainPage.getPageable(), DomainPage.getTotalElements());
    }

}
