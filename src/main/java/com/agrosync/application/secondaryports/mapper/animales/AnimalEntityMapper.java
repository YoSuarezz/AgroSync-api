package com.agrosync.application.secondaryports.mapper.animales;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.domain.animales.AnimalDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalEntityMapper {

    AnimalEntityMapper INSTANCE = Mappers.getMapper(AnimalEntityMapper.class);

    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "venta", ignore = true)
    AnimalEntity toEntity(AnimalDomain domain);

    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "venta", ignore = true)
    AnimalDomain toDomain(AnimalEntity entity);

    List<AnimalEntity> toEntityCollection(List<AnimalDomain> domainCollection);

    List<AnimalDomain> toDomainCollection(List<AnimalEntity> entityCollection);
}
