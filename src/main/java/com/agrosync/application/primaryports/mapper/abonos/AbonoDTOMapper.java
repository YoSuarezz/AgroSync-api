package com.agrosync.application.primaryports.mapper.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.RegistrarAbonoDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.domain.abonos.AbonoDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbonoDTOMapper {

    AbonoDTOMapper INSTANCE = Mappers.getMapper(AbonoDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentaPagar.id", source = "idCuentaPagar")
    AbonoDomain toDomain(RegistrarAbonoDTO dto);

    @Mapping(target = "idCuentaPagar", source = "cuentaPagar.id")
    @Mapping(target = "numeroCuentaPagar", source = "cuentaPagar.numeroCuenta")
    ObtenerAbonoDTO toObtenerDTO(AbonoDomain domain);

    List<ObtenerAbonoDTO> toObtenerDTOCollection(List<AbonoDomain> domainsList);

    default Page<ObtenerAbonoDTO> toObtenerDTOCollection(Page<AbonoDomain> domainPage) {
        List<ObtenerAbonoDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }
}
