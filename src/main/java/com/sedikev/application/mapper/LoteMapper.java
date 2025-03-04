package com.sedikev.application.mapper;

import com.sedikev.application.domain.LoteDomain;
import com.sedikev.application.dto.LoteDTO;
import com.sedikev.domain.entity.LoteEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoteMapper {

    LoteMapper INSTANCE = Mappers.getMapper(LoteMapper.class);

    LoteDTO toDTO(LoteDomain loteDomain);
    LoteDomain toDomain(LoteDTO loteDTO);
    LoteEntity toEntity(LoteDomain loteDomain);
    LoteDomain toDomain(LoteEntity loteEntity);
}