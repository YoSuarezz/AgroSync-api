package com.agrosync.application.primaryports.mapper.cuentascobrar;

import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface CuentaCobrarDTOMapper {

    CuentaCobrarDTOMapper INSTANCE = Mappers.getMapper(CuentaCobrarDTOMapper.class);

    ObtenerCuentaCobrarDTO toObtenerDTO(CuentaCobrarDomain domain);

    List<ObtenerCuentaCobrarDTO> toObtenerDTOCollection(List<CuentaCobrarDomain> domainsList);

    default Page<ObtenerCuentaCobrarDTO> toObtenerDTOCollection(Page<CuentaCobrarDomain> domainPage) {
        List<ObtenerCuentaCobrarDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements()) {
        };
    }
}
