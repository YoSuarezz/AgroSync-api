package com.sedikev.application.mapper;

import com.sedikev.domain.model.LoteDomain;
import com.sedikev.application.dto.LoteDTO;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface LoteMapper {

    @Mapping(source = "id", target = "id")
    LoteDTO toDTO(LoteDomain loteDomain);
    @Mapping(source = "id", target = "id")
    LoteDomain toDomain(LoteDTO loteDTO);
    @Mapping(source = "id", target = "id")
    LoteEntity toEntity(LoteDomain loteDomain);
    @Mapping(source = "id", target = "id")
    LoteDomain toDomain(LoteEntity loteEntity);
}