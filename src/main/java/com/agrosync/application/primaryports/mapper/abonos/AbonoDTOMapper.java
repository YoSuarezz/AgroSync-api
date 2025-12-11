package com.agrosync.application.primaryports.mapper.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.RegistrarAbonoDTO;
import com.agrosync.domain.abonos.AbonoDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AbonoDTOMapper {

    AbonoDTOMapper INSTANCE = Mappers.getMapper(AbonoDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentaPagar", ignore = true)
    @Mapping(target = "fechaPago", ignore = true)
    AbonoDomain toDomain(RegistrarAbonoDTO dto);
}
