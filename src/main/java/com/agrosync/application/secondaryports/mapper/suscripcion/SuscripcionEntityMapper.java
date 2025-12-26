package com.agrosync.application.secondaryports.mapper.suscripcion;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.auth.AuthUserEntityMapper;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthUserEntityMapper.class})
public interface SuscripcionEntityMapper {

    SuscripcionDomain toDomain(SuscripcionEntity suscripcionEntity);

    SuscripcionEntity toEntity(SuscripcionDomain suscripcionDomain);

    List<SuscripcionDomain> toDomainCollection(List<SuscripcionEntity> suscripcionEntity);
}
