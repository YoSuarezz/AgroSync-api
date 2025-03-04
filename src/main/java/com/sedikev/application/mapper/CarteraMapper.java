package com.sedikev.application.mapper;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.domain.entity.Cartera;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CarteraMapper {

    CarteraDTO toDTO(Cartera cartera);
    Cartera toEntity(CarteraDTO carteraDTO);

    List<CarteraDTO> toDTOList(List<Cartera> carteras);
    List<Cartera> toEntityList(List<CarteraDTO> carteraDTOs);
}
