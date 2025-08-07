package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<VentaEntity, Long> {

    VentaEntity save(VentaEntity ventaEntity);

    Optional<VentaEntity> findById(Long id);

    void deleteById(Long id);

    List<VentaEntity> findAll();

    List<VentaEntity> findByUsuarioId(Long usuarioId);
}
