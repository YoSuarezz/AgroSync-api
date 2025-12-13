package com.agrosync.application.secondaryports.mapper.cobros;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CobroEntityMapper {

    @Mapping(target = "cuentaCobrar", ignore = true)
    CobroEntity toEntity(CobroDomain domain);

    @Mapping(target = "cuentaCobrar", source = "cuentaCobrar", qualifiedByName = "mapCuentaCobrarBasico")
    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    CobroDomain toDomain(CobroEntity entity);

    List<CobroEntity> toEntityCollection(List<CobroDomain> domainCollection);

    List<CobroDomain> toDomainCollection(List<CobroEntity> entityCollection);

    @Named("mapCuentaCobrarBasico")
    default CuentaCobrarDomain mapCuentaCobrarBasico(com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity entity) {
        if (entity == null) {
            return null;
        }
        CuentaCobrarDomain domain = new CuentaCobrarDomain();
        domain.setId(entity.getId());
        domain.setNumeroCuenta(entity.getNumeroCuenta());
        return domain;
    }
}
