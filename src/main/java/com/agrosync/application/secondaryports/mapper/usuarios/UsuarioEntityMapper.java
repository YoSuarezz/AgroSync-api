package com.agrosync.application.secondaryports.mapper.usuarios;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        TipoUsuarioEntityMapper.class,
        CarteraEntityMapper.class,
        CompraEntityMapper.class,
        CuentaPagarEntityMapper.class,
        VentaEntityMapper.class,
        CuentaCobrarEntityMapper.class
})
public interface UsuarioEntityMapper {

    UsuarioEntityMapper INSTANCE = Mappers.getMapper(UsuarioEntityMapper.class);

    UsuarioEntity toEntity(UsuarioDomain domain);

    UsuarioDomain toDomain(UsuarioEntity entity);

    List<UsuarioEntity> toEntityCollection(List<UsuarioDomain> domainCollection);

    List<UsuarioDomain> toDomainCollection(List<UsuarioEntity> entityCollection);
}
