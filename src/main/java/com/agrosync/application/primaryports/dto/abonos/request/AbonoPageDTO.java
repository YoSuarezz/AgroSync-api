package com.agrosync.application.primaryports.dto.abonos.request;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.UUID;

public class AbonoPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerAbonoDTO abono;
    private MetodoPagoEnum metodoPago;
    private UUID cuentaPagarId;
    private UUID suscripcionId;

    public AbonoPageDTO() {
    }

    public AbonoPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerAbonoDTO abono,
            MetodoPagoEnum metodoPago, UUID cuentaPagarId, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setAbono(abono);
        setMetodoPago(metodoPago);
        setCuentaPagarId(cuentaPagarId);
        setSuscripcionId(suscripcionId);
    }

    public static AbonoPageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerAbonoDTO abono,
            MetodoPagoEnum metodoPago, UUID cuentaPagarId, UUID suscripcionId) {
        return new AbonoPageDTO(page, size, sortBy, sortDirection, abono, metodoPago, cuentaPagarId, suscripcionId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public ObtenerAbonoDTO getAbono() {
        return abono;
    }

    public void setAbono(ObtenerAbonoDTO abono) {
        this.abono = ObjectHelper.getDefault(abono, ObtenerAbonoDTO.create());
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public UUID getCuentaPagarId() {
        return cuentaPagarId;
    }

    public void setCuentaPagarId(UUID cuentaPagarId) {
        this.cuentaPagarId = cuentaPagarId;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
