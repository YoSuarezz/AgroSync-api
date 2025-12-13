package com.agrosync.application.primaryports.mapper.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.domain.cobros.CobroDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CobroDTOMapper {

    CobroDTOMapper INSTANCE = Mappers.getMapper(CobroDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentaCobrar.id", source = "idCuentaCobrar")
    CobroDomain toDomain(RegistrarCobroDTO dto);

    @Mapping(target = "idCuentaCobrar", source = "cuentaCobrar.id")
    @Mapping(target = "numeroCuentaCobrar", source = "cuentaCobrar.numeroCuenta")
    ObtenerCobroDTO toObtenerDTO(CobroDomain domain);

    List<ObtenerCobroDTO> toObtenerDTOCollection(List<CobroDomain> domainsList);

    default Page<ObtenerCobroDTO> toObtenerDTOCollection(Page<CobroDomain> domainPage) {
        List<ObtenerCobroDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }
}
