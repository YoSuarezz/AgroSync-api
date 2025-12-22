package com.agrosync.application.secondaryports.repository;

import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
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

    /**
     * Busca cuentas por pagar pendientes o parcialmente pagadas ordenadas por fecha de emisión (FIFO).
     */
    List<CuentaPagarEntity> findByProveedor_IdAndSuscripcion_IdAndEstadoInOrderByFechaEmisionAsc(
            UUID proveedorId, UUID suscripcionId, List<EstadoCuentaEnum> estados);

    List<CuentaPagarEntity> findByProveedor_IdAndSuscripcion_Id(UUID proveedorId, UUID suscripcionId);
}
