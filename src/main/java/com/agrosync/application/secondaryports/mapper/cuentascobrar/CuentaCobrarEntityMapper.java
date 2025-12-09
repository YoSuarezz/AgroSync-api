package com.agrosync.application.secondaryports.mapper.cuentascobrar;

import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioEntityMapper.class, CobroEntityMapper.class})
public interface CuentaCobrarEntityMapper {

    @Mapping(target = "venta", ignore = true)
    CuentaCobrarEntity toEntity(CuentaCobrarDomain domain);

    @Mapping(target = "venta", ignore = true)
    CuentaCobrarDomain toDomain(CuentaCobrarEntity entity);

    List<CuentaCobrarEntity> toEntityCollection(List<CuentaCobrarDomain> domainCollection);

    List<CuentaCobrarDomain> toDomainCollection(List<CuentaCobrarEntity> entityCollection);
}
