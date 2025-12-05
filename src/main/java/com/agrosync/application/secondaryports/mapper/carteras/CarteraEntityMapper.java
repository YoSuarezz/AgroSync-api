package com.agrosync.application.secondaryports.mapper.carteras;

import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.domain.carteras.CarteraDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarteraEntityMapper {

    @Mapping(target = "usuario", ignore = true)
    CarteraEntity toEntity(CarteraDomain domain);

    @Mapping(target = "usuario", ignore = true)
    CarteraDomain toDomain(CarteraEntity entity);

    List<CarteraEntity> toEntityCollection(List<CarteraDomain> domainCollection);

    List<CarteraDomain> toDomainCollection(List<CarteraEntity> entityCollection);
}
