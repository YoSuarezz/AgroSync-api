package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>, JpaSpecificationExecutor<UsuarioEntity> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndSuscripcion_Id(String nombre, UUID suscripcionId);

    boolean existsByNombreAndIdNot(String nombre, UUID id);

    boolean existsByNombreAndIdNotAndSuscripcion_Id(String nombre, UUID id, UUID suscripcionId);

    boolean existsByTelefono(String telefono);

    boolean existsByTelefonoAndSuscripcion_Id(String telefono, UUID suscripcionId);

    boolean existsByTelefonoAndIdNot(String telefono, UUID id);

    boolean existsByTelefonoAndIdNotAndSuscripcion_Id(String telefono, UUID id, UUID suscripcionId);

    java.util.Optional<UsuarioEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    boolean existsByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);
}
