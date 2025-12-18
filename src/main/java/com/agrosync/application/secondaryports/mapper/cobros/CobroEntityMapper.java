package com.agrosync.application.secondaryports.mapper.cobros;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CobroEntityMapper {

    @Mapping(target = "cuentaCobrar", source = "cuentaCobrar", qualifiedByName = "mapCuentaCobrarDomainToEntity")
    @Mapping(target = "suscripcion", source = "suscripcionId", qualifiedByName = "mapSuscripcionIdToEntity")
    CobroEntity toEntity(CobroDomain domain);

    @Mapping(target = "cuentaCobrar", source = "cuentaCobrar", qualifiedByName = "mapCuentaCobrarEntityToDomain")
    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    CobroDomain toDomain(CobroEntity entity);

    List<CobroEntity> toEntityCollection(List<CobroDomain> domainCollection);

    List<CobroDomain> toDomainCollection(List<CobroEntity> entityCollection);

    @Named("mapCuentaCobrarDomainToEntity")
    default CuentaCobrarEntity mapCuentaCobrarDomainToEntity(CuentaCobrarDomain domain) {
        if (domain == null || domain.getId() == null) {
            return null;
        }
        return CuentaCobrarEntity.create(domain.getId());
    }

    @Named("mapCuentaCobrarEntityToDomain")
    default CuentaCobrarDomain mapCuentaCobrarEntityToDomain(CuentaCobrarEntity entity) {
        if (entity == null) {
            return null;
        }
        CuentaCobrarDomain domain = new CuentaCobrarDomain();
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
