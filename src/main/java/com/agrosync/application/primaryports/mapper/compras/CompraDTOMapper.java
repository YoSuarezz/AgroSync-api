package com.agrosync.application.primaryports.mapper.compras;

import com.agrosync.application.primaryports.dto.compras.request.RegistrarNuevaCompraDTO;
import com.agrosync.application.primaryports.mapper.lotes.LoteDTOMapper;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(uses = {LoteDTOMapper.class, UsuarioDTOMapper.class})
public interface CompraDTOMapper {

    CompraDTOMapper INSTANCE = Mappers.getMapper(CompraDTOMapper.class);

    @Mapping(target = "proveedor", source = "proveedorId")
    CompraDomain toDomain(RegistrarNuevaCompraDTO dto);

    default UsuarioDomain map(UUID proveedorId) {
        return UsuarioDomain.create(proveedorId);
    }
}
