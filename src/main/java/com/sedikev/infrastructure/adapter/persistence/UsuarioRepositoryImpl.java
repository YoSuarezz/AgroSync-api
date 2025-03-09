package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
import com.sedikev.domain.repository.UsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositoryImpl extends JpaRepository<UsuarioEntity, Long>, UsuarioRepository {
}
