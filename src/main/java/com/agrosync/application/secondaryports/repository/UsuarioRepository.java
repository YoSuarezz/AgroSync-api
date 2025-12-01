package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>, JpaSpecificationExecutor<UsuarioEntity> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreAndIdNot(String nombre, UUID id);

    boolean existsByTelefono(String telefono);

    boolean existsByTelefonoAndIdNot(String telefono, UUID id);
}
