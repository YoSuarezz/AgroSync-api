package com.agrosync.application.secondaryports.mapper.lotes;

import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.domain.lotes.LoteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AnimalEntityMapper.class})
public interface LoteEntityMapper {

    LoteEntityMapper INSTANCE = Mappers.getMapper(LoteEntityMapper.class);

    @Mapping(target = "compra", ignore = true)
    LoteEntity toEntity(LoteDomain domain);

    @Mapping(target = "compra", ignore = true)
    LoteDomain toDomain(LoteEntity entity);

    List<LoteEntity> toEntityCollection(List<LoteDomain> domainCollection);

    List<LoteDomain> toDomainCollection(List<LoteEntity> entityCollection);
}
