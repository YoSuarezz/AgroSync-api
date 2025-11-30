package com.agrosync.application.secondaryports.mapper.usuarios;

import com.agrosync.application.secondaryports.entity.usuarios.TipoUsuarioEntity;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoUsuarioEntityMapper {

    TipoUsuarioEntityMapper INSTANCE = Mappers.getMapper(TipoUsuarioEntityMapper.class);

    TipoUsuarioEntity toEntity(TipoUsuarioDomain domain);

    TipoUsuarioDomain toDomain(TipoUsuarioEntity entity);

    List<TipoUsuarioEntity> toEntityCollection(List<TipoUsuarioDomain> domainCollection);

    List<TipoUsuarioDomain> toDomainCollection(List<TipoUsuarioEntity> entityCollection);
}
