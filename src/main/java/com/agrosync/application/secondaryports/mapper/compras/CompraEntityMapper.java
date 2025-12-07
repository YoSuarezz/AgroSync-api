package com.agrosync.application.secondaryports.mapper.compras;

import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.mapper.lotes.LoteEntityMapper;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.domain.compras.CompraDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {UsuarioEntityMapper.class, LoteEntityMapper.class, CuentaPagarEntityMapper.class})
public interface CompraEntityMapper {

    CompraEntity toEntity(CompraDomain domain);

    CompraDomain toDomain(CompraEntity entity);

    List<CompraEntity> toEntityCollection(List<CompraDomain> domainCollection);

    List<CompraDomain> toDomainCollection(List<CompraEntity> entityCollection);
}
