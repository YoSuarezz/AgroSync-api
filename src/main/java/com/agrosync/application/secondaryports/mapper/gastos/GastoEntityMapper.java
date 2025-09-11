package com.agrosync.application.secondaryports.mapper.gastos;

import com.agrosync.application.secondaryports.entity.gastos.GastoEntity;
import com.agrosync.domain.model.GastoDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GastoEntityMapper {

    GastoEntityMapper INSTANCE = Mappers.getMapper(GastoEntityMapper.class);

    GastoEntity toEntity(GastoDomain domain);

    GastoDomain toDomain(GastoEntity entity);

    List<GastoEntity> toEntityCollection(List<GastoDomain> domainCollection);

    List<GastoDomain> toDomainCollection(List<GastoEntity> entityCollection);
}
