package com.agrosync.application.primaryports.dto.cuentaspagar.response;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.crosscutting.helpers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerCuentaPagarDTO {

    private UUID id;
    private String numeroCuenta;
    private ObtenerUsuarioDTO proveedor;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private EstadoCuentaEnum estado;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public ObtenerCuentaPagarDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroCuenta(TextHelper.EMPTY);
        setProveedor(ObtenerUsuarioDTO.create());
        setMontoTotal(BigDecimal.ZERO);
        setSaldoPendiente(BigDecimal.ZERO);
        setEstado(null);
        setFechaEmision(DateHelper.getDefault());
        setFechaVencimiento(DateHelper.getDefault());
    }

    public ObtenerCuentaPagarDTO(UUID id, String numeroCuenta, ObtenerUsuarioDTO proveedor, BigDecimal montoTotal, BigDecimal saldoPendiente, EstadoCuentaEnum estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        setId(id);
        setNumeroCuenta(numeroCuenta);
        setProveedor(proveedor);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setEstado(estado);
        setFechaEmision(fechaEmision);
        setFechaVencimiento(fechaVencimiento);
    }

    public static ObtenerCuentaPagarDTO create(UUID id, String numeroCuenta, ObtenerUsuarioDTO proveedor, BigDecimal montoTotal, BigDecimal saldoPendiente, EstadoCuentaEnum estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        return new ObtenerCuentaPagarDTO(id, numeroCuenta, proveedor, montoTotal, saldoPendiente, estado, fechaEmision, fechaVencimiento);
    }

    public static ObtenerCuentaPagarDTO create() {
        return new ObtenerCuentaPagarDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = TextHelper.applyTrim(numeroCuenta);
    }

    public ObtenerUsuarioDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ObtenerUsuarioDTO proveedor) {
        this.proveedor = ObjectHelper.getDefault(proveedor, ObtenerUsuarioDTO.create());
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = NumericHelper.getDefault(montoTotal, BigDecimal.ZERO);
    }

    public EstadoCuentaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaEnum estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = DateHelper.getDefault(fechaEmision, DateHelper.getDefault());
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = DateHelper.getDefault(fechaVencimiento, DateHelper.getDefault());
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = NumericHelper.getDefault(saldoPendiente, BigDecimal.ZERO);
    }
}
