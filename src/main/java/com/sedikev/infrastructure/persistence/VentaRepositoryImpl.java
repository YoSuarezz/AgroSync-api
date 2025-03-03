package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Venta;
import com.sedikev.domain.repository.VentaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VentaRepositoryImpl extends JpaRepository<Venta, Long>, VentaRepository {
}
