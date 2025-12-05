package com.agrosync.application.secondaryports.mapper.usuarios;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {
    
    @Mapping(target = "suscripcion", source = "suscripcionId")
    UsuarioEntity toEntity(UsuarioDomain domain);

    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    UsuarioDomain toDomain(UsuarioEntity entity);

    List<UsuarioEntity> toEntityCollection(List<UsuarioDomain> domainCollection);

    List<UsuarioDomain> toDomainCollection(List<UsuarioEntity> entityCollection);

    default SuscripcionEntity map(UUID suscripcionId) {
        return SuscripcionEntity.create(suscripcionId);
    }
}
