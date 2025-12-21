package com.agrosync.application.primaryports.mapper.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.RegistrarAnimalVentaDTO;
import com.agrosync.application.primaryports.dto.ventas.request.RegistrarNuevaVentaDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentaDetalleDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.application.primaryports.mapper.cuentascobrar.CuentaCobrarDTOMapper;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.ventas.VentaDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(uses = {AnimalDTOMapper.class, UsuarioDTOMapper.class, CuentaCobrarDTOMapper.class})
public interface VentaDTOMapper {

    VentaDTOMapper INSTANCE = Mappers.getMapper(VentaDTOMapper.class);

    @Mapping(target = "cliente", expression = "java(mapCliente(dto.getClienteId()))")
    @Mapping(target = "animales", expression = "java(mapAnimales(dto.getAnimales(), dto.getSuscripcionId()))")
    VentaDomain toDomain(RegistrarNuevaVentaDTO dto);

    default UsuarioDomain mapCliente(UUID clienteId) {
        return UsuarioDomain.create(UUIDHelper.getDefault(clienteId, UUIDHelper.getDefault()));
    }

    default List<AnimalDomain> mapAnimales(List<RegistrarAnimalVentaDTO> animales, UUID suscripcionId) {
        if (animales == null) {
            return new ArrayList<>();
        }
        return animales.stream()
                .map(dto -> buildAnimalDomain(dto, suscripcionId))
                .collect(Collectors.toList());
    }

    default AnimalDomain buildAnimalDomain(RegistrarAnimalVentaDTO dto, UUID ventaSuscripcionId) {
        var animal = new AnimalDomain();
        animal.setId(dto.getAnimalId());
        animal.setPrecioKiloVenta(dto.getPrecioKiloVenta());
        animal.setSuscripcionId(ObjectHelper.getDefault(dto.getSuscripcionId(), ventaSuscripcionId));
        return animal;
    }

    ObtenerVentasDTO toObtenerDTO(VentaDomain domain);

    List<ObtenerVentasDTO> toObtenerDTOCollection(List<VentaDomain> domainList);

    default Page<ObtenerVentasDTO> toObtenerDTOCollection(Page<VentaDomain> domainPage) {
        List<ObtenerVentasDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }

    ObtenerVentaDetalleDTO toObtenerDetalleDTO(VentaDomain domain);

    default UUID map(CuentaCobrarDomain cuentaCobrar) {
        if (cuentaCobrar == null || cuentaCobrar.getId() == null) {
            return UUIDHelper.getDefault();
        }
        return cuentaCobrar.getId();
    }
}
