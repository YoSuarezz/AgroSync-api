package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaPagarRepository extends JpaRepository<CuentaPagarEntity, UUID>, JpaSpecificationExecutor<CuentaPagarEntity> {

    Optional<CuentaPagarEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    List<CuentaPagarEntity> findByProveedor_IdAndSuscripcion_IdAndEstadoNot(UUID proveedorId, UUID suscripcionId, EstadoCuentaEnum estado);
}
