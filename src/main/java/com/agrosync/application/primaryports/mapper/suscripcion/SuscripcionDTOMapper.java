package com.agrosync.application.primaryports.mapper.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarEstadoSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarPlanSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.RegistrarSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionPorIdDTO;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface SuscripcionDTOMapper {

    SuscripcionDTOMapper INSTANCE = Mappers.getMapper(SuscripcionDTOMapper.class);

    SuscripcionDomain toDomain(SuscripcionDTO suscripcionDTO);

    SuscripcionDomain toDomain(RegistrarSuscripcionDTO registrarSuscripcionDTO);

    @Mapping(target = "fechaUltimoPago", source = "fechaUltimoPago")
    SuscripcionDomain toDomain(ActualizarEstadoSuscripcionDTO actualizarEstadoSuscripcionDTO);

    SuscripcionDomain toDomain(ActualizarPlanSuscripcionDTO actualizarPlanSuscripcionDTO);

    ObtenerSuscripcionDTO toObtenerSuscripcionDTO(SuscripcionDomain domain);

    ObtenerSuscripcionPorIdDTO toObtenerSuscripcionPorIdDTO(SuscripcionDomain domain);

    default Page<ObtenerSuscripcionDTO> toDTOCollection(Page<SuscripcionDomain> page) {
        return page.map(this::toObtenerSuscripcionDTO);
    }

    SuscripcionDTO toDto(SuscripcionDomain suscripcionDomain);

    List<SuscripcionDomain> toDomainCollection(List<SuscripcionDTO> suscripcionDTO);
}
