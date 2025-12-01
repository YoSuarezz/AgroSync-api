package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CuentaCobrarRepository extends JpaRepository<CuentaCobrarEntity, UUID> {
}
