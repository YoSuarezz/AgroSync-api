package com.agrosync.application.primaryports.dto.abonos.request;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class RegistrarAbonoDTO {

    private UUID idCuentaPagar;
    private BigDecimal monto;
    private MetodoPagoEnum metodoPago;
    private UUID suscripcionId;

    public RegistrarAbonoDTO() {
        setIdCuentaPagar(null);
        setMonto(BigDecimal.ZERO);
        setMetodoPago(MetodoPagoEnum.OTRO);
        setSuscripcionId(null);
    }

    public RegistrarAbonoDTO(UUID idCuentaPagar, BigDecimal monto, MetodoPagoEnum metodoPago, UUID suscripcionId) {
        setIdCuentaPagar(idCuentaPagar);
        setMonto(monto);
        setMetodoPago(metodoPago);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarAbonoDTO create(UUID idCuentaPagar, BigDecimal monto, MetodoPagoEnum metodoPago,
            UUID suscripcionId) {
        return new RegistrarAbonoDTO(idCuentaPagar, monto, metodoPago, suscripcionId);
    }

    public static RegistrarAbonoDTO create() {
        return new RegistrarAbonoDTO();
    }

    public UUID getIdCuentaPagar() {
        return idCuentaPagar;
    }

    public RegistrarAbonoDTO setIdCuentaPagar(UUID idCuentaPagar) {
        this.idCuentaPagar = idCuentaPagar;
        return this;
    }

    public BigDecimal getMonto() {
        return ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public RegistrarAbonoDTO setMonto(BigDecimal monto) {
        this.monto = ObjectHelper.getDefault(monto, BigDecimal.ZERO);
        return this;
    }

    public MetodoPagoEnum getMetodoPago() {
        return ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }

    public RegistrarAbonoDTO setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
        return this;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public RegistrarAbonoDTO setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
        return this;
    }
}
