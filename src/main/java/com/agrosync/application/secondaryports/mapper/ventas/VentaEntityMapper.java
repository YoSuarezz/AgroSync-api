package com.agrosync.application.secondaryports.mapper.ventas;

import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.ventas.VentaDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UsuarioEntityMapper.class, AnimalEntityMapper.class, CuentaCobrarEntityMapper.class})
public interface VentaEntityMapper {

    VentaEntityMapper INSTANCE = Mappers.getMapper(VentaEntityMapper.class);

    VentaEntity toEntity(VentaDomain domain);

    VentaDomain toDomain(VentaEntity entity);

    List<VentaEntity> toEntityCollection(List<VentaDomain> domainCollection);

    List<VentaDomain> toDomainCollection(List<VentaEntity> entityCollection);
}
