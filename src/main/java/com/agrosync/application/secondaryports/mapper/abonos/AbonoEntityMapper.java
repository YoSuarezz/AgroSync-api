package com.agrosync.application.secondaryports.mapper.abonos;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AbonoEntityMapper {

    @Mapping(target = "cuentaPagar", source = "cuentaPagar", qualifiedByName = "mapCuentaPagarDomainToEntity")
    @Mapping(target = "suscripcion", source = "suscripcionId", qualifiedByName = "mapSuscripcionIdToEntity")
    AbonoEntity toEntity(AbonoDomain domain);

    @Mapping(target = "cuentaPagar", source = "cuentaPagar", qualifiedByName = "mapCuentaPagarEntityToDomain")
    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    AbonoDomain toDomain(AbonoEntity entity);

    List<AbonoEntity> toEntityCollection(List<AbonoDomain> domainCollection);

    List<AbonoDomain> toDomainCollection(List<AbonoEntity> entityCollection);

    @Named("mapCuentaPagarDomainToEntity")
    default CuentaPagarEntity mapCuentaPagarDomainToEntity(CuentaPagarDomain domain) {
        if (domain == null || domain.getId() == null) {
            return null;
        }
        return CuentaPagarEntity.create(domain.getId());
    }

    @Named("mapCuentaPagarEntityToDomain")
    default CuentaPagarDomain mapCuentaPagarEntityToDomain(CuentaPagarEntity entity) {
        if (entity == null) {
            return null;
        }
        CuentaPagarDomain domain = new CuentaPagarDomain();
        domain.setId(entity.getId());
        domain.setNumeroCuenta(entity.getNumeroCuenta());
        return domain;
    }

    @Named("mapSuscripcionIdToEntity")
    default SuscripcionEntity mapSuscripcionIdToEntity(UUID suscripcionId) {
        if (suscripcionId == null) {
            return null;
        }
        return SuscripcionEntity.create(suscripcionId);
    }
}
