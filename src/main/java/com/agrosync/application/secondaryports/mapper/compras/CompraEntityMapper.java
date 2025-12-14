package com.agrosync.application.secondaryports.mapper.compras;

import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.lotes.LoteEntityMapper;
import com.agrosync.domain.compras.CompraDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;


@Mapper(componentModel = "spring", uses = {LoteEntityMapper.class})
public interface CompraEntityMapper {

    @Mapping(target = "proveedor", expression = "java(mapProveedorDomainToEntity(domain.getProveedor()))")
    @Mapping(target = "cuentaPagar", expression = "java(mapCuentaPagarDomainToEntity(domain.getCuentaPagar()))")
    CompraEntity toEntity(CompraDomain domain);

    @Mapping(target = "proveedor", expression = "java(mapProveedorEntityToDomain(entity.getProveedor()))")
    @Mapping(target = "cuentaPagar", expression = "java(mapCuentaPagarEntityToDomain(entity.getCuentaPagar()))")
    CompraDomain toDomain(CompraEntity entity);

    List<CompraEntity> toEntityCollection(List<CompraDomain> domainCollection);

    List<CompraDomain> toDomainCollection(List<CompraEntity> entityCollection);

    default UsuarioEntity mapProveedorDomainToEntity(UsuarioDomain proveedor) {
        if (proveedor == null || proveedor.getId() == null) {
            return null;
        }
        return UsuarioEntity.create(proveedor.getId());
    }

    default UsuarioDomain mapProveedorEntityToDomain(UsuarioEntity proveedor) {
        if (proveedor == null || proveedor.getId() == null) {
            return null;
        }
        UsuarioDomain domain = new UsuarioDomain();
        domain.setId(proveedor.getId());
        domain.setNombre(proveedor.getNombre());
        domain.setTelefono(proveedor.getTelefono());
        domain.setTipoUsuario(proveedor.getTipoUsuario());
        return domain;
    }

    default CuentaPagarEntity mapCuentaPagarDomainToEntity(CuentaPagarDomain cuentaPagar) {
        if (cuentaPagar == null || cuentaPagar.getId() == null) {
            return null;
        }
        return CuentaPagarEntity.create(cuentaPagar.getId());
    }

    default CuentaPagarDomain mapCuentaPagarEntityToDomain(CuentaPagarEntity cuentaPagar) {
        if (cuentaPagar == null || cuentaPagar.getId() == null) {
            return null;
        }
        CuentaPagarDomain domain = new CuentaPagarDomain();
        domain.setId(cuentaPagar.getId());
        domain.setNumeroCuenta(cuentaPagar.getNumeroCuenta());
        domain.setProveedor(mapProveedorEntityToDomain(cuentaPagar.getProveedor()));
        domain.setMontoTotal(cuentaPagar.getMontoTotal());
        domain.setSaldoPendiente(cuentaPagar.getSaldoPendiente());
        domain.setEstado(cuentaPagar.getEstado());
        domain.setFechaEmision(cuentaPagar.getFechaEmision());
        domain.setFechaVencimiento(cuentaPagar.getFechaVencimiento());
        if (cuentaPagar.getSuscripcion() != null) {
            domain.setSuscripcionId(cuentaPagar.getSuscripcion().getId());
        }
        return domain;
    }
}
