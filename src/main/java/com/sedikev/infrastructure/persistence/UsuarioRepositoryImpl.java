package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.UsuarioEntity;
import com.sedikev.domain.repository.UsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositoryImpl extends JpaRepository<UsuarioEntity, Long>, UsuarioRepository {
}
