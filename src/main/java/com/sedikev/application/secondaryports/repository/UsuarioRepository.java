package com.sedikev.application.secondaryports.repository;

import com.sedikev.application.secondaryports.entity.usuarios.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity save(UsuarioEntity usuarioEntity);

    Optional<UsuarioEntity> findById(Long id);

    void deleteById(Long id);

    List<UsuarioEntity> findAll();

    boolean existsByNombre(String nombre);

    boolean existsByTelefono(String telefono);
}
