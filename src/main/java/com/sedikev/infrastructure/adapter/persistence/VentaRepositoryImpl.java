package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.VentaEntity;
import com.sedikev.domain.repository.VentaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepositoryImpl extends JpaRepository<VentaEntity, Long>, VentaRepository {
}
