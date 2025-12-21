package com.agrosync.application.secondaryports.mapper.carteras;

import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarteraEntityMapper {

    @Mapping(target = "usuario", expression = "java(mapUsuarioDomainToEntity(domain.getUsuario()))")
    CarteraEntity toEntity(CarteraDomain domain);

    @Mapping(target = "usuario", expression = "java(mapUsuarioEntityToDomain(entity.getUsuario()))")
    CarteraDomain toDomain(CarteraEntity entity);

    List<CarteraEntity> toEntityCollection(List<CarteraDomain> domainCollection);

    List<CarteraDomain> toDomainCollection(List<CarteraEntity> entityCollection);

    default UsuarioEntity mapUsuarioDomainToEntity(UsuarioDomain usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        return UsuarioEntity.create(usuario.getId());
    }

    default UsuarioDomain mapUsuarioEntityToDomain(UsuarioEntity usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        UsuarioDomain domain = new UsuarioDomain();
        domain.setId(usuario.getId());
        domain.setNombre(usuario.getNombre());
        domain.setTelefono(usuario.getTelefono());
        domain.setTipoUsuario(usuario.getTipoUsuario());
        return domain;
    }

}
