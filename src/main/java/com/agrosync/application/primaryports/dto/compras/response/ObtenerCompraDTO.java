package com.agrosync.application.primaryports.dto.compras.response;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerCompraDTO {

    private UUID id;
    private String numeroCompra;
    private ObtenerUsuarioDTO proveedor;
    private LocalDate fechaCompra;
    private BigDecimal precioTotalCompra;
    private UUID loteId;
    private UUID cuentaPagarId;

    public ObtenerCompraDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroCompra(TextHelper.EMPTY);
        setProveedor(ObtenerUsuarioDTO.create());
        setFechaCompra(LocalDate.now());
        setPrecioTotalCompra(BigDecimal.ZERO);
        setLoteId(UUIDHelper.getDefault());
        setCuentaPagarId(UUIDHelper.getDefault());
    }

    public ObtenerCompraDTO(UUID id, String numeroCompra, ObtenerUsuarioDTO proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, UUID loteId, UUID cuentaPagarId) {
        setId(id);
        setNumeroCompra(numeroCompra);
        setProveedor(proveedor);
        setFechaCompra(fechaCompra);
        setPrecioTotalCompra(precioTotalCompra);
        setLoteId(loteId);
        setCuentaPagarId(cuentaPagarId);
    }

    public static ObtenerCompraDTO create(UUID id, String numeroCompra, ObtenerUsuarioDTO proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, UUID loteId, UUID cuentaPagarId) {
        return new ObtenerCompraDTO(id, numeroCompra, proveedor, fechaCompra, precioTotalCompra, loteId, cuentaPagarId);
    }

    public static ObtenerCompraDTO create() {
        return new ObtenerCompraDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = TextHelper.getDefault(numeroCompra);
    }

    public ObtenerUsuarioDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ObtenerUsuarioDTO proveedor) {
        this.proveedor = ObjectHelper.getDefault(proveedor, ObtenerUsuarioDTO.create());
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = ObjectHelper.getDefault(fechaCompra, LocalDate.now());
    }

    public BigDecimal getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(BigDecimal precioTotalCompra) {
        this.precioTotalCompra = ObjectHelper.getDefault(precioTotalCompra, BigDecimal.ZERO);
    }

    public UUID getLoteId() {
        return loteId;
    }

    public void setLoteId(UUID loteId) {
        this.loteId = UUIDHelper.getDefault(loteId, UUIDHelper.getDefault());
    }

    public UUID getCuentaPagarId() {
        return cuentaPagarId;
    }

    public void setCuentaPagarId(UUID cuentaPagarId) {
        this.cuentaPagarId = UUIDHelper.getDefault(cuentaPagarId, UUIDHelper.getDefault());
    }
}
