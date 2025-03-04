package com.sedikev.application.mapper;

import com.sedikev.application.dto.LoteDTO;
import com.sedikev.domain.entity.Lote;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    LoteDTO toDTO(Lote lote);
    Lote toEntity(LoteDTO loteDTO);

    List<LoteDTO> toDTOList(List<Lote> lotes);
    List<Lote> toEntityList(List<LoteDTO> loteDTOs);
}
