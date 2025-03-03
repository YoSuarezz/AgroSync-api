package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Usuario;
import com.sedikev.domain.repository.UsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepositoryImpl extends JpaRepository<Usuario, Long>, UsuarioRepository {
}
