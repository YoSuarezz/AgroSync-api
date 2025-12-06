package com.agrosync.application.primaryports.mapper.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.RegistrarNuevoLoteDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.domain.lotes.LoteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(uses = {AnimalDTOMapper.class})
public interface LoteDTOMapper {

    LoteDTOMapper INSTANCE = Mappers.getMapper(LoteDTOMapper.class);

    LoteDomain toDomain(RegistrarNuevoLoteDTO dto);

    ObtenerLoteDTO toObtenerDTO(LoteDomain domain);

    List<ObtenerLoteDTO> toObtenerDTOCollection(List<LoteDomain> domainList);

    default Page<ObtenerLoteDTO> toObtenerDTOCollection(Page<LoteDomain> DomainPage) {
        List<ObtenerLoteDTO> dtoList = toObtenerDTOCollection(DomainPage.getContent());
        return new PageImpl<>(dtoList, DomainPage.getPageable(), DomainPage.getTotalElements());
    }
}
