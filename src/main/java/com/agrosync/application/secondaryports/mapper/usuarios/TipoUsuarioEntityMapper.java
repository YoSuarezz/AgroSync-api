package com.agrosync.application.secondaryports.mapper.usuarios;

import com.agrosync.application.secondaryports.entity.usuarios.TIpoUsuarioEntity;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoUsuarioEntityMapper {

    TipoUsuarioEntityMapper INSTANCE = Mappers.getMapper(TipoUsuarioEntityMapper.class);

    TIpoUsuarioEntity toEntity(TipoUsuarioDomain domain);

    TipoUsuarioDomain toDomain(TIpoUsuarioEntity entity);

    List<TIpoUsuarioEntity> toEntityCollection(List<TipoUsuarioDomain> domainCollection);

    List<TipoUsuarioDomain> toDomainCollection(List<TIpoUsuarioEntity> entityCollection);
}
