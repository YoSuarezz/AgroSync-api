package com.agrosync.application.secondaryports.mapper.auth;

import com.agrosync.application.secondaryports.entity.auth.AuthUserEntity;
import com.agrosync.domain.auth.AuthUserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthUserEntityMapper {

    AuthUserEntityMapper INSTANCE = Mappers.getMapper(AuthUserEntityMapper.class);

    AuthUserEntity toEntity(AuthUserDomain domain);

    AuthUserDomain toDomain(AuthUserEntity entity);

    List<AuthUserEntity> toEntityCollection(List<AuthUserDomain> domainCollection);

    List<AuthUserDomain> toDomainCollection(List<AuthUserEntity> entityCollection);
}
