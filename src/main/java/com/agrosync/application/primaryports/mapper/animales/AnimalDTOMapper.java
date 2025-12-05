package com.agrosync.application.primaryports.mapper.animales;

import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.domain.animales.AnimalDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimalDTOMapper {

    AnimalDTOMapper INSTANCE = Mappers.getMapper(AnimalDTOMapper.class);

    AnimalDomain toDomain(RegistrarNuevoAnimalDTO dto);
}
