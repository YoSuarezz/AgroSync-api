package com.agrosync.application.primaryports.mapper.carteras;

import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CarteraDTOMapper {

    CarteraDTOMapper INSTANCE = Mappers.getMapper(CarteraDTOMapper.class);

    ObtenerCarteraDTO toObtenerDTO(CarteraDomain domain);

    List<ObtenerCarteraDTO> toObtenerDTOCollection(List<CarteraDomain> domainCollection);

    default Page<ObtenerCarteraDTO> toObtenerDTOCollection(Page<CarteraDomain> domainPage) {
        List<ObtenerCarteraDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }

    default UsuarioDomain map(UUID usuarioId) {
        return UsuarioDomain.create(usuarioId);
    }
}
