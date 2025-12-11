package com.agrosync.application.primaryports.mapper.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
import com.agrosync.domain.cobros.CobroDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CobroDTOMapper {

    CobroDTOMapper INSTANCE = Mappers.getMapper(CobroDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentaCobrar", ignore = true)
    @Mapping(target = "fechaCobro", ignore = true)
    CobroDomain toDomain(RegistrarCobroDTO dto);
}
