package com.agrosync.application.secondaryports.mapper.cuentaspagar;

import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UsuarioEntityMapper.class, AbonoEntityMapper.class})
public interface CuentaPagarEntityMapper {

    CuentaPagarEntityMapper INSTANCE = Mappers.getMapper(CuentaPagarEntityMapper.class);

    @Mapping(target = "compra", ignore = true)
    CuentaPagarEntity toEntity(CuentaPagarDomain domain);

    @Mapping(target = "compra", ignore = true)
    CuentaPagarDomain toDomain(CuentaPagarEntity entity);

    List<CuentaPagarEntity> toEntityCollection(List<CuentaPagarDomain> domainCollection);

    List<CuentaPagarDomain> toDomainCollection(List<CuentaPagarEntity> entityCollection);
}
