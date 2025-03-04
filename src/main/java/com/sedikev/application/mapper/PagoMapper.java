package com.sedikev.application.mapper;

import com.sedikev.application.dto.PagoDTO;
import com.sedikev.domain.entity.Pago;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    PagoDTO toDTO(Pago pago);
    Pago toEntity(PagoDTO pagoDTO);

    List<PagoDTO> toDTOList(List<Pago> pagos);
    List<Pago> toEntityList(List<PagoDTO> pagoDTOs);
}
