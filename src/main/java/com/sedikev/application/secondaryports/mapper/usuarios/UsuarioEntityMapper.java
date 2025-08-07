package com.sedikev.application.secondaryports.mapper.usuarios;

import com.sedikev.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.sedikev.domain.usuarios.UsuarioDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UsuarioEntityMapper {

    UsuarioEntityMapper INSTANCE = Mappers.getMapper(UsuarioEntityMapper.class);

    UsuarioEntity toEntity(UsuarioDomain domain);

    UsuarioDomain toDomain(UsuarioEntity entity);

    List<UsuarioEntity> toEntityCollection(List<UsuarioDomain> domainCollection);

    List<UsuarioDomain> toDomainCollection(List<UsuarioEntity> entityCollection);
}
