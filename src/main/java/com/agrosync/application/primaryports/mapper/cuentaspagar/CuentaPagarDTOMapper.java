package com.agrosync.application.primaryports.mapper.cuentaspagar;

import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.mapper.abonos.AbonoDTOMapper;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper
public interface CuentaPagarDTOMapper {

    CuentaPagarDTOMapper INSTANCE = Mappers.getMapper(CuentaPagarDTOMapper.class);

    ObtenerCuentaPagarDTO toObtenerDTO(CuentaPagarDomain domain);

    List<ObtenerCuentaPagarDTO> toObtenerDTOCollection(List<CuentaPagarDomain> domainsList);

    default Page<ObtenerCuentaPagarDTO> toObtenerDTOCollection(Page<CuentaPagarDomain> domainPage) {
        List<ObtenerCuentaPagarDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements()) {
        };
    }
}
