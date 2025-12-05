package com.agrosync.application.secondaryports.mapper.abonos;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.domain.abonos.AbonoDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbonoEntityMapper {

    @Mapping(target = "cuentaPagar", ignore = true)
    AbonoEntity toEntity(AbonoDomain domain);

    @Mapping(target = "cuentaPagar", ignore = true)
    AbonoDomain toDomain(AbonoEntity entity);

    List<AbonoEntity> toEntityCollection(List<AbonoDomain> domainCollection);

    List<AbonoDomain> toDomainCollection(List<AbonoEntity> entityCollection);
}
