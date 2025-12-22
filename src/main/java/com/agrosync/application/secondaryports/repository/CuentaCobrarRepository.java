package com.agrosync.application.secondaryports.repository;

import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaCobrarRepository extends JpaRepository<CuentaCobrarEntity, UUID>, JpaSpecificationExecutor<CuentaCobrarEntity> {

    Optional<CuentaCobrarEntity> findByIdAndSuscripcion_Id(UUID id, UUID suscripcionId);

    /**
     * Busca cuentas por cobrar pendientes o parcialmente cobradas ordenadas por fecha de emisión (FIFO).
     */
    List<CuentaCobrarEntity> findByCliente_IdAndSuscripcion_IdAndEstadoInOrderByFechaEmisionAsc(
            UUID clienteId, UUID suscripcionId, List<EstadoCuentaEnum> estados);

    List<CuentaCobrarEntity> findByCliente_IdAndSuscripcion_Id(UUID clienteId, UUID suscripcionId);
}
