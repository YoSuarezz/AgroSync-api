package com.agrosync.application.primaryports.mapper.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.ActualizarUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.RegistrarNuevoUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDetalladoDTO;
import com.agrosync.application.primaryports.mapper.carteras.CarteraDTOMapper;
import com.agrosync.application.primaryports.mapper.compras.CompraDTOMapper;
import com.agrosync.application.primaryports.mapper.cuentascobrar.CuentaCobrarDTOMapper;
import com.agrosync.application.primaryports.mapper.cuentaspagar.CuentaPagarDTOMapper;
import com.agrosync.application.primaryports.mapper.ventas.VentaDTOMapper;
import com.agrosync.domain.usuarios.UsuarioDetalladoDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(uses = {CarteraDTOMapper.class, CompraDTOMapper.class, VentaDTOMapper.class,
        CuentaCobrarDTOMapper.class, CuentaPagarDTOMapper.class})
public interface UsuarioDTOMapper {

    UsuarioDTOMapper INSTANCE = Mappers.getMapper(UsuarioDTOMapper.class);

    UsuarioDomain toDomain(RegistrarNuevoUsuarioDTO dto);

    UsuarioDomain toDomain(ActualizarUsuarioDTO dto);

    ObtenerUsuarioDTO toObtenerDTO(UsuarioDomain domain);

    List<ObtenerUsuarioDTO> toObtenerDTOCollection(List<UsuarioDomain> domainList);

    ObtenerUsuarioDetalladoDTO toObtenerDetalleDTO(UsuarioDetalladoDomain domain);

    default Page<ObtenerUsuarioDTO> toObtenerDTOCollection(Page<UsuarioDomain> domainPage) {
        List<ObtenerUsuarioDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }

}
