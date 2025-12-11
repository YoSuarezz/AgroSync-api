package com.agrosync.application.secondaryports.mapper.usuarios;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = { CarteraEntityMapper.class })
public interface UsuarioEntityMapper {

    @Mapping(target = "suscripcion", source = "suscripcionId")
    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "cuentasPagar", ignore = true)
    @Mapping(target = "cuentasCobrar", ignore = true)
    UsuarioEntity toEntity(UsuarioDomain domain);

    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    @Mapping(target = "cartera.usuario", ignore = true)
    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "cuentasPagar", ignore = true)
    @Mapping(target = "cuentasCobrar", ignore = true)
    UsuarioDomain toDomain(UsuarioEntity entity);

    List<UsuarioEntity> toEntityCollection(List<UsuarioDomain> domainCollection);

    List<UsuarioDomain> toDomainCollection(List<UsuarioEntity> entityCollection);

    default SuscripcionEntity map(UUID suscripcionId) {
        return SuscripcionEntity.create(suscripcionId);
    }
}
