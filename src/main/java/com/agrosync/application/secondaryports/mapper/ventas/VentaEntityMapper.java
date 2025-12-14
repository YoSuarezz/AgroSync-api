package com.agrosync.application.secondaryports.mapper.ventas;

import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AnimalEntityMapper.class, CuentaCobrarEntityMapper.class})
public interface VentaEntityMapper {

    VentaEntityMapper INSTANCE = Mappers.getMapper(VentaEntityMapper.class);

    @Mapping(target = "cliente", qualifiedByName = "ventaClienteToEntity")
    @Mapping(target = "animales", source = "animales")
    @Mapping(target = "cuentaCobrar", ignore = true)
    @Mapping(target = "suscripcion.id", source = "suscripcionId")
    VentaEntity toEntity(VentaDomain domain);

    @Mapping(target = "cliente", qualifiedByName = "ventaClienteToDomain")
    @Mapping(target = "animales", source = "animales")
    @Mapping(target = "cuentaCobrar", source = "cuentaCobrar")
    @Mapping(target = "suscripcionId", expression = "java(entity.getSuscripcion() != null ? entity.getSuscripcion().getId() : null)")
    VentaDomain toDomain(VentaEntity entity);

    List<VentaEntity> toEntityCollection(List<VentaDomain> domainCollection);

    List<VentaDomain> toDomainCollection(List<VentaEntity> entityCollection);

    @Named("ventaClienteToEntity")
    default UsuarioEntity mapClienteDomainToEntity(UsuarioDomain cliente) {
        if (cliente == null || cliente.getId() == null) {
            return null;
        }
        return UsuarioEntity.create(cliente.getId());
    }

    @Named("ventaClienteToDomain")
    default UsuarioDomain mapClienteEntityToDomain(UsuarioEntity cliente) {
        if (cliente == null || cliente.getId() == null) {
            return null;
        }
        UsuarioDomain domain = new UsuarioDomain();
        domain.setId(cliente.getId());
        domain.setNombre(cliente.getNombre());
        domain.setTelefono(cliente.getTelefono());
        domain.setTipoUsuario(cliente.getTipoUsuario());
        return domain;
    }

    default CuentaCobrarEntity mapCuentaCobrarDomainToEntity(com.agrosync.domain.cuentascobrar.CuentaCobrarDomain cuentaCobrar) {
        if (cuentaCobrar == null || cuentaCobrar.getId() == null) {
            return null;
        }
        return CuentaCobrarEntity.create(cuentaCobrar.getId());
    }
}
