package com.agrosync.application.primaryports.mapper.gastos;

import com.agrosync.domain.model.GastoDomain;
import com.agrosync.application.primaryports.dto.gastos.GastoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper()
public interface GastoDTOMapper {

    GastoDTOMapper INSTANCE = Mappers.getMapper(GastoDTOMapper.class);

    GastoDomain toDomain(GastoDTO dto);

    GastoDTO toDTO(GastoDomain domain);


    List<GastoDTO> toDTOCollection(List<GastoDomain> domainList);

    default Page<GastoDTO> toDTOCollection(Page<GastoDomain> DomainPage) {
        List<GastoDTO> dtoList = toDTOCollection(DomainPage.getContent());
        return new PageImpl<>(dtoList, DomainPage.getPageable(), DomainPage.getTotalElements());
    }

}
