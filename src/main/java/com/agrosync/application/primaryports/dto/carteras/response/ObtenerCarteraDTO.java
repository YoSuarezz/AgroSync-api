package com.agrosync.application.primaryports.dto.carteras.response;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class ObtenerCarteraDTO {

    private UUID id;
    private ObtenerUsuarioDTO usuario;
    private BigDecimal saldoActual;
    private BigDecimal totalCuentasPagar;
    private BigDecimal totalCuentasCobrar;

    public ObtenerCarteraDTO() {
        setId(UUIDHelper.getDefault());
        setUsuario(ObtenerUsuarioDTO.create());
        setSaldoActual(BigDecimal.ZERO);
        setTotalCuentasPagar(BigDecimal.ZERO);
        setTotalCuentasCobrar(BigDecimal.ZERO);
    }

    public ObtenerCarteraDTO(UUID id, ObtenerUsuarioDTO usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar) {
        setId(id);
        setUsuario(usuario);
        setSaldoActual(saldoActual);
        setTotalCuentasPagar(totalCuentasPagar);
        setTotalCuentasCobrar(totalCuentasCobrar);
    }

    public static ObtenerCarteraDTO create(UUID id, ObtenerUsuarioDTO usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar) {
        return new ObtenerCarteraDTO(id, usuario, saldoActual, totalCuentasPagar, totalCuentasCobrar);
    }

    public static ObtenerCarteraDTO create() {
        return new ObtenerCarteraDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public ObtenerUsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(ObtenerUsuarioDTO usuario) {
        this.usuario = ObjectHelper.getDefault(usuario, ObtenerUsuarioDTO.create());
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = ObjectHelper.getDefault(saldoActual, BigDecimal.ZERO);
    }

    public BigDecimal getTotalCuentasPagar() {
        return totalCuentasPagar;
    }

    public void setTotalCuentasPagar(BigDecimal totalCuentasPagar) {
        this.totalCuentasPagar = ObjectHelper.getDefault(totalCuentasPagar, BigDecimal.ZERO);
    }

    public BigDecimal getTotalCuentasCobrar() {
        return totalCuentasCobrar;
    }

    public void setTotalCuentasCobrar(BigDecimal totalCuentasCobrar) {
        this.totalCuentasCobrar = ObjectHelper.getDefault(totalCuentasCobrar, BigDecimal.ZERO);
    }
}
