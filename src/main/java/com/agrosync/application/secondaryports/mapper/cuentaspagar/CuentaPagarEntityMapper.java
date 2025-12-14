package com.agrosync.application.secondaryports.mapper.cuentaspagar;

import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AbonoEntityMapper.class})
public interface CuentaPagarEntityMapper {

    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "proveedor", source = "proveedor", qualifiedByName = "cxpUsuarioDomainToEntity")
    CuentaPagarEntity toEntity(CuentaPagarDomain domain);

    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "proveedor", source = "proveedor", qualifiedByName = "cxpUsuarioEntityToDomain")
    CuentaPagarDomain toDomain(CuentaPagarEntity entity);

    List<CuentaPagarEntity> toEntityCollection(List<CuentaPagarDomain> domainCollection);

    List<CuentaPagarDomain> toDomainCollection(List<CuentaPagarEntity> entityCollection);

    @Named("cxpUsuarioDomainToEntity")
    default UsuarioEntity mapUsuarioDomainToEntity(UsuarioDomain usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        return UsuarioEntity.create(usuario.getId());
    }

    @Named("cxpUsuarioEntityToDomain")
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
