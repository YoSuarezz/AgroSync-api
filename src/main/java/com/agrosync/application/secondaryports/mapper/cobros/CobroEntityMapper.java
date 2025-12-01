package com.agrosync.application.secondaryports.mapper.cobros;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.domain.cobros.CobroDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CobroEntityMapper {

    CobroEntityMapper INSTANCE = Mappers.getMapper(CobroEntityMapper.class);

    @Mapping(target = "cuentaCobrar", ignore = true)
    CobroEntity toEntity(CobroDomain domain);

    @Mapping(target = "cuentaCobrar", ignore = true)
    CobroDomain toDomain(CobroEntity entity);

    List<CobroEntity> toEntityCollection(List<CobroDomain> domainCollection);

    List<CobroDomain> toDomainCollection(List<CobroEntity> entityCollection);
}
