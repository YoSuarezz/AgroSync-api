package com.agrosync.application.primaryports.mapper.compras;

import com.agrosync.application.primaryports.dto.compras.request.RegistrarNuevaCompraDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.mapper.lotes.LoteDTOMapper;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

@Mapper(uses = {LoteDTOMapper.class, UsuarioDTOMapper.class})
public interface CompraDTOMapper {

    CompraDTOMapper INSTANCE = Mappers.getMapper(CompraDTOMapper.class);

    @Mapping(target = "proveedor", source = "proveedorId")
    CompraDomain toDomain(RegistrarNuevaCompraDTO dto);

    @Mapping(target = "loteId", source = "lote.id")
    @Mapping(target = "cuentaPagarId", source = "cuentaPagar.id")
    ObtenerCompraDTO toObtenerDTO(CompraDomain domain);

    List<ObtenerCompraDTO> toObtenerDTOCollection(List<CompraDomain> domainCollection);

    default Page<ObtenerCompraDTO> toObtenerDTOCollection(Page<CompraDomain> domainPage) {
        List<ObtenerCompraDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }

    default UsuarioDomain map(UUID proveedorId) {
        return UsuarioDomain.create(proveedorId);
    }
}
