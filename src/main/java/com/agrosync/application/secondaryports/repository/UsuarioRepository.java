package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

    void deleteById(Long id);

    boolean existsByNombre(String nombre);

    boolean existsByTelefono(String telefono);
}
