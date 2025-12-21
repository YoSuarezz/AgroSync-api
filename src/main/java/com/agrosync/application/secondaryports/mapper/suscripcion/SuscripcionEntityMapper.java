package com.agrosync.application.secondaryports.mapper.suscripcion;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.auth.AuthUserEntityMapper;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthUserEntityMapper.class})
public interface SuscripcionEntityMapper {

    SuscripcionEntityMapper INSTANCE = Mappers.getMapper(SuscripcionEntityMapper.class);

    SuscripcionDomain toDomain(SuscripcionEntity suscripcionEntity);

    SuscripcionEntity toEntity(SuscripcionDomain suscripcionDomain);

    List<SuscripcionDomain> toDomainCollection(List<SuscripcionEntity> suscripcionEntity);
}
