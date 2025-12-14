package com.agrosync.application.primaryports.dto.compras.response;

import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerCompraDetalleDTO {

    private UUID id;
    private String numeroCompra;
    private ObtenerUsuarioDTO proveedor;
    private LocalDate fechaCompra;
    private BigDecimal precioTotalCompra;
    private ObtenerLoteDTO lote;
    private ObtenerCuentaPagarDTO cuentaPagar;

    public ObtenerCompraDetalleDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroCompra(TextHelper.EMPTY);
        setProveedor(ObtenerUsuarioDTO.create());
        setFechaCompra(null);
        setPrecioTotalCompra(BigDecimal.ZERO);
        setLote(ObtenerLoteDTO.create());
        setCuentaPagar(ObtenerCuentaPagarDTO.create());
    }

    public ObtenerCompraDetalleDTO(UUID id, String numeroCompra, ObtenerUsuarioDTO proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, ObtenerLoteDTO lote, ObtenerCuentaPagarDTO cuentaPagar) {
        setId(id);
        setNumeroCompra(numeroCompra);
        setProveedor(proveedor);
        setFechaCompra(fechaCompra);
        setPrecioTotalCompra(precioTotalCompra);
        setLote(lote);
        setCuentaPagar(cuentaPagar);
    }

    public static ObtenerCompraDetalleDTO create(UUID id, String numeroCompra, ObtenerUsuarioDTO proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, ObtenerLoteDTO lote, ObtenerCuentaPagarDTO cuentaPagar) {
        return new ObtenerCompraDetalleDTO(id, numeroCompra, proveedor, fechaCompra, precioTotalCompra, lote, cuentaPagar);
    }

    public static ObtenerCompraDetalleDTO create() {
        return new ObtenerCompraDetalleDTO();
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
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(BigDecimal precioTotalCompra) {
        this.precioTotalCompra = precioTotalCompra;
    }

    public ObtenerLoteDTO getLote() {
        return lote;
    }

    public void setLote(ObtenerLoteDTO lote) {
        this.lote = ObjectHelper.getDefault(lote, ObtenerLoteDTO.create());
    }

    public ObtenerCuentaPagarDTO getCuentaPagar() {
        return cuentaPagar;
    }

    public void setCuentaPagar(ObtenerCuentaPagarDTO cuentaPagar) {
        this.cuentaPagar = ObjectHelper.getDefault(cuentaPagar, ObtenerCuentaPagarDTO.create());
    }
}
