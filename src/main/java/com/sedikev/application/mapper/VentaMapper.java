package com.sedikev.application.mapper;

import com.sedikev.application.dto.VentaDTO;
import com.sedikev.domain.entity.Venta;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    VentaDTO toDTO(Venta venta);
    Venta toEntity(VentaDTO ventaDTO);

    List<VentaDTO> toDTOList(List<Venta> ventas);
    List<Venta> toEntityList(List<VentaDTO> ventaDTOs);
}
