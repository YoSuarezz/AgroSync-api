package com.agrosync.application.primaryports.dto.cuentascobrar.response;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.crosscutting.helpers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerCuentaCobrarDTO {

    private UUID id;
    private String numeroCuenta;
    private ObtenerUsuarioDTO cliente;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private EstadoCuentaEnum estado;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public ObtenerCuentaCobrarDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroCuenta(TextHelper.EMPTY);
        setCliente(ObtenerUsuarioDTO.create());
        setMontoTotal(BigDecimal.ZERO);
        setSaldoPendiente(BigDecimal.ZERO);
        setEstado(null);
        setFechaEmision(null);
        setFechaVencimiento(null);
    }

    public ObtenerCuentaCobrarDTO(UUID id, String numeroCuenta, ObtenerUsuarioDTO cliente, BigDecimal montoTotal, BigDecimal saldoPendiente, EstadoCuentaEnum estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        setId(id);
        setNumeroCuenta(numeroCuenta);
        setCliente(cliente);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setEstado(estado);
        setFechaEmision(fechaEmision);
        setFechaVencimiento(fechaVencimiento);
    }

    public static ObtenerCuentaCobrarDTO create(UUID id, String numeroCuenta, ObtenerUsuarioDTO cliente, BigDecimal montoTotal, BigDecimal saldoPendiente, EstadoCuentaEnum estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        return new ObtenerCuentaCobrarDTO(id, numeroCuenta, cliente, montoTotal, saldoPendiente, estado, fechaEmision, fechaVencimiento);
    }

    public static ObtenerCuentaCobrarDTO create() {
        return new ObtenerCuentaCobrarDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoCuentaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaEnum estado) {
        this.estado = estado;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = NumericHelper.getDefault(saldoPendiente, BigDecimal.ZERO);
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = NumericHelper.getDefault(montoTotal, BigDecimal.ZERO);
    }

    public ObtenerUsuarioDTO getCliente() {
        return cliente;
    }

    public void setCliente(ObtenerUsuarioDTO cliente) {
        this.cliente = ObjectHelper.getDefault(cliente, ObtenerUsuarioDTO.create());
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = TextHelper.applyTrim(numeroCuenta);
    }
}