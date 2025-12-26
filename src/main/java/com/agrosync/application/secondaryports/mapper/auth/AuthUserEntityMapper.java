package com.agrosync.application.secondaryports.mapper.auth;

import com.agrosync.application.secondaryports.entity.auth.AuthUserEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.domain.auth.AuthUserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AuthUserEntityMapper {

    @Mapping(target = "suscripcion", source = "suscripcionId")
    AuthUserEntity toEntity(AuthUserDomain domain);

    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    AuthUserDomain toDomain(AuthUserEntity entity);

    List<AuthUserEntity> toEntityCollection(List<AuthUserDomain> domainCollection);

    List<AuthUserDomain> toDomainCollection(List<AuthUserEntity> entityCollection);

    default SuscripcionEntity map(UUID suscripcionId) {
        if (suscripcionId == null) {
            return null;
        }
        return SuscripcionEntity.create(suscripcionId);
    }
}
