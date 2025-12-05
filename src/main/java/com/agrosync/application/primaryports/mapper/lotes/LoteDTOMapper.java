package com.agrosync.application.primaryports.mapper.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.RegistrarNuevoLoteDTO;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.domain.lotes.LoteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AnimalDTOMapper.class})
public interface LoteDTOMapper {

    LoteDTOMapper INSTANCE = Mappers.getMapper(LoteDTOMapper.class);

    LoteDomain toDomain(RegistrarNuevoLoteDTO dto);
}
