package com.sedikev.application.mapper;

import com.sedikev.application.dto.GastoDTO;
import com.sedikev.domain.entity.Gasto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GastoMapper {

    GastoDTO toDTO(Gasto gasto);
    Gasto toEntity(GastoDTO gastoDTO);

    List<GastoDTO> toDTOList(List<Gasto> gastos);
    List<Gasto> toEntityList(List<GastoDTO> gastoDTOs);
}

