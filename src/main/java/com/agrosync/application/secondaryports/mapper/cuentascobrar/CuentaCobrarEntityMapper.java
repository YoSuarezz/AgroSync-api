package com.agrosync.application.secondaryports.mapper.cuentascobrar;

import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CobroEntityMapper.class})
public interface CuentaCobrarEntityMapper {

    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cxcUsuarioDomainToEntity")
    CuentaCobrarEntity toEntity(CuentaCobrarDomain domain);

    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cxcUsuarioEntityToDomain")
    CuentaCobrarDomain toDomain(CuentaCobrarEntity entity);

    List<CuentaCobrarEntity> toEntityCollection(List<CuentaCobrarDomain> domainCollection);

    List<CuentaCobrarDomain> toDomainCollection(List<CuentaCobrarEntity> entityCollection);

    @Named("cxcUsuarioDomainToEntity")
    default UsuarioEntity mapUsuarioDomainToEntity(UsuarioDomain usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        return UsuarioEntity.create(usuario.getId());
    }

    @Named("cxcUsuarioEntityToDomain")
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
