package com.agrosync.application.primaryports.dto.compras.request;

import com.agrosync.application.primaryports.dto.lotes.request.RegistrarNuevoLoteDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.time.LocalDate;
import java.util.UUID;

public class RegistrarNuevaCompraDTO {

    private UUID proveedorId;
    private LocalDate fechaCompra;
    private RegistrarNuevoLoteDTO lote;
    private UUID suscripcionId;

    public RegistrarNuevaCompraDTO() {
        setProveedorId(UUIDHelper.getDefault());
        setFechaCompra(LocalDate.now());
        setLote(RegistrarNuevoLoteDTO.create());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public RegistrarNuevaCompraDTO(UUID proveedorId, LocalDate fechaCompra, RegistrarNuevoLoteDTO lote, UUID suscripcionId) {
        setProveedorId(proveedorId);
        setFechaCompra(fechaCompra);
        setLote(lote);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarNuevaCompraDTO create(UUID proveedorId, LocalDate fechaCompra, RegistrarNuevoLoteDTO lote, UUID suscripcionId) {
        return new RegistrarNuevaCompraDTO(proveedorId, fechaCompra, lote, suscripcionId);
    }

    public static RegistrarNuevaCompraDTO create() {
        return new RegistrarNuevaCompraDTO();
    }

    public UUID getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(UUID proveedorId) {
        this.proveedorId = UUIDHelper.getDefault(proveedorId, UUIDHelper.getDefault());
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = ObjectHelper.getDefault(fechaCompra, LocalDate.now());
    }

    public RegistrarNuevoLoteDTO getLote() {
        return lote;
    }

    public void setLote(RegistrarNuevoLoteDTO lote) {
        this.lote = ObjectHelper.getDefault(lote, RegistrarNuevoLoteDTO.create());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
