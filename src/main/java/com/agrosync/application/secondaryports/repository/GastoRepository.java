package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.gastos.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GastoRepository extends JpaRepository<GastoEntity, Long>, JpaSpecificationExecutor<GastoEntity> {
}
