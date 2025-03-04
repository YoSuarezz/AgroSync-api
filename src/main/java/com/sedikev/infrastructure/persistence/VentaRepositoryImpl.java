package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.VentaEntity;
import com.sedikev.domain.repository.VentaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepositoryImpl extends JpaRepository<VentaEntity, Long>, VentaRepository {
}
