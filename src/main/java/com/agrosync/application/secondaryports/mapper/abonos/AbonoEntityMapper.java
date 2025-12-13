package com.agrosync.application.secondaryports.mapper.abonos;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbonoEntityMapper {

    @Mapping(target = "cuentaPagar", ignore = true)
    AbonoEntity toEntity(AbonoDomain domain);

    @Mapping(target = "cuentaPagar", source = "cuentaPagar", qualifiedByName = "mapCuentaPagarBasico")
    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    AbonoDomain toDomain(AbonoEntity entity);

    List<AbonoEntity> toEntityCollection(List<AbonoDomain> domainCollection);

    List<AbonoDomain> toDomainCollection(List<AbonoEntity> entityCollection);

    @Named("mapCuentaPagarBasico")
    default CuentaPagarDomain mapCuentaPagarBasico(com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity entity) {
        if (entity == null) {
            return null;
        }
        CuentaPagarDomain domain = new CuentaPagarDomain();
        domain.setId(entity.getId());
        domain.setNumeroCuenta(entity.getNumeroCuenta());
        return domain;
    }
}
