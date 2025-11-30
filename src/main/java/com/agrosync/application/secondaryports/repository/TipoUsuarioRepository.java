package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.usuarios.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {

    Optional<TipoUsuarioEntity> findByNombreIgnoreCase(String nombre);
}
