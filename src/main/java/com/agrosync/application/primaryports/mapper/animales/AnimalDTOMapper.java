package com.agrosync.application.primaryports.mapper.animales;

import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.domain.animales.AnimalDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface AnimalDTOMapper {

    AnimalDTOMapper INSTANCE = Mappers.getMapper(AnimalDTOMapper.class);

    AnimalDomain toDomain(RegistrarNuevoAnimalDTO dto);

    ObtenerAnimalDTO toObtenerDTO(AnimalDomain domain);

    List<ObtenerAnimalDTO> toObtenerDTOCollection(List<AnimalDomain> domainList);

    default Page<ObtenerAnimalDTO> toObtenerDTOCollection(Page<AnimalDomain> domainPage) {
        List<ObtenerAnimalDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }
}
